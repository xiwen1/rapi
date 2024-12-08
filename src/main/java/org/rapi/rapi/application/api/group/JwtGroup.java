package org.rapi.rapi.application.api.group;

import io.vavr.Tuple;
import io.vavr.Tuple3;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import lombok.Getter;
import org.rapi.rapi.application.api.endpoint.Endpoint;
import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.endpoint.Response;
import org.rapi.rapi.application.api.endpoint.RestfulEndpoint;
import org.rapi.rapi.application.api.endpoint.route.ConstantFragment;
import org.rapi.rapi.application.api.endpoint.route.Route;
import org.rapi.rapi.application.api.structure.schema.NumberSchema;
import org.rapi.rapi.application.api.structure.schema.ObjectSchema;
import org.rapi.rapi.application.api.structure.schema.Schema;
import org.rapi.rapi.application.api.structure.schema.StringSchema;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;

@Getter
public class JwtGroup extends Group {

    private static final Schema JWT_SCHEMA = ObjectSchema.create()
        .addField("accessToken", StringSchema.create()).addField("expiresIn", NumberSchema.create())
        .addField("refreshToken", StringSchema.create());
    private static final Schema CREDENTIAL_SCHEMA = ObjectSchema.create()
        .addField("username", StringSchema.create()).addField("password", StringSchema.create());
    private static final RestfulEndpoint LOGIN_ENDPOINT = RestfulEndpoint.create("Login", "login",
        Option.some(CREDENTIAL_SCHEMA), HttpMethod.POST,
        Route.create(ConstantFragment.create("auth"), ConstantFragment.create("login")),
        List.of(Response.create(HttpStatusCode.valueOf(200), "OK", JWT_SCHEMA),
            Response.create(HttpStatusCode.valueOf(401), "Unauthorized", ObjectSchema.create())));
    private static final RestfulEndpoint REFRESH_ENDPOINT = RestfulEndpoint.create("Refresh",
        "refresh",
        Option.some(ObjectSchema.create().addField("refreshToken", StringSchema.create())),
        HttpMethod.POST,
        Route.create(ConstantFragment.create("auth"), ConstantFragment.create("refresh")),
        List.of(Response.create(HttpStatusCode.valueOf(200), "OK", JWT_SCHEMA),
            Response.create(HttpStatusCode.valueOf(401), "Unauthorized", ObjectSchema.create())));
    private final EndpointId loginEndpointId;
    private final EndpointId refreshEndpointId;
    private Map<EndpointId, EndpointId> protectedEndpointsMap;

    private JwtGroup(GroupId id, Map<EndpointId, EndpointId> protectedEndpointsMap,
        EndpointId loginEndpointId, EndpointId refreshEndpointId) {
        super(id);
        this.protectedEndpointsMap = protectedEndpointsMap;
        this.loginEndpointId = loginEndpointId;
        this.refreshEndpointId = refreshEndpointId;
    }

    public static JwtGroup fromRaw(GroupId id, Map<EndpointId, EndpointId> generatedEndpointsMap,
        EndpointId loginEndpointId, EndpointId refreshEndpointId) {
        return new JwtGroup(id, generatedEndpointsMap, loginEndpointId, refreshEndpointId);
    }

    public static JwtGroup create(Map<EndpointId, EndpointId> generatedEndpointsMap,
        EndpointId loginEndpointId, EndpointId refreshEndpointId) {
        return new JwtGroup(GroupId.create(), generatedEndpointsMap, loginEndpointId,
            refreshEndpointId);
    }

    public static Tuple3<JwtGroup, RestfulEndpoint, RestfulEndpoint> create() {
        var loginEndpointId = EndpointId.create();
        var refreshEndpointId = EndpointId.create();
        var jwtGroup = new JwtGroup(GroupId.create(), HashMap.empty(), loginEndpointId,
            refreshEndpointId);
        var loginEndpoint = RestfulEndpoint.fromCopy(loginEndpointId, LOGIN_ENDPOINT);
        var refreshEndpoint = RestfulEndpoint.fromCopy(refreshEndpointId, REFRESH_ENDPOINT);
        return Tuple.of(jwtGroup, loginEndpoint, refreshEndpoint);
    }

    @Override
    public List<EndpointId> getGeneratedEndpoints() {
        return protectedEndpointsMap.values().toList().append(loginEndpointId)
            .append(refreshEndpointId);
    }

    public RestfulEndpoint add(RestfulEndpoint sourceEndpoint) {
        if (protectedEndpointsMap.containsKey(sourceEndpoint.getId()) || sourceEndpoint.getId()
            .equals(loginEndpointId) || sourceEndpoint.getId().equals(refreshEndpointId)) {
            throw new IllegalArgumentException(
                "Source endpoint already exists in the group or is a login/refresh endpoint");
        }
        var protectedEndpoint = generateProtectedEndpoint(sourceEndpoint);
        protectedEndpointsMap = protectedEndpointsMap.put(sourceEndpoint.getId(),
            protectedEndpoint.getId());
        return protectedEndpoint;
    }

    public EndpointId remove(EndpointId sourceEndpointId) {
        if (sourceEndpointId.equals(loginEndpointId) || sourceEndpointId.equals(
            refreshEndpointId)) {
            throw new IllegalArgumentException("Login and refresh endpoints cannot be removed");
        }
        if (!protectedEndpointsMap.keySet().contains(sourceEndpointId)) {
            throw new IllegalArgumentException("Source endpoint is not part of the group");
        }
        var protectedEndpointId = protectedEndpointsMap.get(sourceEndpointId)
            .getOrElseThrow(() -> new IllegalArgumentException("Source endpoint not found"));
        protectedEndpointsMap = protectedEndpointsMap.remove(sourceEndpointId);
        return protectedEndpointId;
    }

    public List<RestfulEndpoint> dissolve(List<RestfulEndpoint> originalProtectedEndpoints,
        RestfulEndpoint originalLoginEndpoint, RestfulEndpoint originalRefreshEndpoint) {
        if (!originalProtectedEndpoints.map(Endpoint::getId).toSet()
            .equals(protectedEndpointsMap.values().toSet()) || !originalLoginEndpoint.getId()
            .equals(loginEndpointId) || !originalRefreshEndpoint.getId()
            .equals(refreshEndpointId)) {
            throw new IllegalArgumentException("Original endpoints do not match the current group");
        }
        return originalProtectedEndpoints.append(originalLoginEndpoint)
            .append(originalRefreshEndpoint);
    }

    public List<RestfulEndpoint> regenerate(List<RestfulEndpoint> sourceEndpoints) {
        if (!sourceEndpoints.map(Endpoint::getId).toSet().equals(protectedEndpointsMap.keySet())) {
            throw new IllegalArgumentException("Source endpoints do not match the current group");
        }

        return protectedEndpointsMap.toList().map(sourceProtectedEndpointIdTuple -> {
            var sourceEndpointId = sourceProtectedEndpointIdTuple._1;
            var protectedEndpointId = sourceProtectedEndpointIdTuple._2;
            var sourceEndpoint = sourceEndpoints.find(
                    endpoint -> endpoint.getId().equals(sourceEndpointId))
                .getOrElseThrow(() -> new IllegalArgumentException("Source endpoint not found"));
            return RestfulEndpoint.fromCopy(protectedEndpointId,
                generateProtectedEndpoint(sourceEndpoint));
        });
    }

    private RestfulEndpoint generateProtectedEndpoint(RestfulEndpoint sourceEndpoint) {
        var protectedEndpoint = RestfulEndpoint.fromRaw(EndpointId.create(),
            sourceEndpoint.getTitle(), sourceEndpoint.getDescription(), sourceEndpoint.getRequest(),
            sourceEndpoint.getHeader()
                .map(header -> header.addField("Authorization", StringSchema.create())),
            sourceEndpoint.getQuery(), sourceEndpoint.getMethod(), sourceEndpoint.getRoute(),
            sourceEndpoint.getResponses().append(
                Response.create(HttpStatusCode.valueOf(401), "Unauthorized",
                    ObjectSchema.create())));
        protectedEndpointsMap = protectedEndpointsMap.put(sourceEndpoint.getId(),
            protectedEndpoint.getId());
        return protectedEndpoint;
    }
}
