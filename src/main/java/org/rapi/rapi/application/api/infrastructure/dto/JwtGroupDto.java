package org.rapi.rapi.application.api.infrastructure.dto;


import io.vavr.collection.Map;
import lombok.Getter;
import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.group.GroupId;
import org.rapi.rapi.application.api.group.JwtGroup;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document("jwt_group")
public class JwtGroupDto {

    @Id
    private String _id;
    private final EndpointId loginEndpointId;
    private final EndpointId refreshEndpointId;
    private final Map<EndpointId, EndpointId> protectedEndpointsMap;

    public JwtGroupDto(String _id, EndpointId loginEndpointId, EndpointId refreshEndpointId,
        Map<EndpointId, EndpointId> protectedEndpointsMap) {
        this._id = _id;
        this.loginEndpointId = loginEndpointId;
        this.refreshEndpointId = refreshEndpointId;
        this.protectedEndpointsMap = protectedEndpointsMap;
    }

    public static JwtGroupDto fromDomain(JwtGroup domain) {
        return new JwtGroupDto(
            domain.getId().id().toString(),
            domain.getLoginEndpointId(),
            domain.getRefreshEndpointId(),
            domain.getProtectedEndpointsMap()
        );
    }

    public JwtGroup toDomain() {
        return JwtGroup.fromRaw(
            GroupId.fromString(get_id()),
            protectedEndpointsMap,
            loginEndpointId,
            refreshEndpointId
        );
    }
}
