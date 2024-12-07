package org.rapi.rapi.application.api.group.effectfulbehavior;

import io.vavr.Tuple3;
import io.vavr.collection.Map;
import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.endpoint.RestfulEndpoint;
import org.rapi.rapi.application.api.endpoint.effectfulbehavior.EndpointPersistence;
import org.rapi.rapi.application.api.group.JwtGroup;

public class CreateJwtGroupBehavior {

    private final GroupPersistence groupPersistence;
    private final EndpointPersistence endpointPersistence;

    public CreateJwtGroupBehavior(GroupPersistence groupPersistence,
        EndpointPersistence endpointPersistence) {
        this.groupPersistence = groupPersistence;
        this.endpointPersistence = endpointPersistence;
    }

    public JwtGroup createJwtGroup(Map<EndpointId, EndpointId> generatedEndpointsMap,
        EndpointId loginEndpointId, EndpointId refreshEndpointId) {
        var group = JwtGroup.create(generatedEndpointsMap, loginEndpointId, refreshEndpointId);
        groupPersistence.saveJwt(group);
        return group;
    }

    public Tuple3<JwtGroup, RestfulEndpoint, RestfulEndpoint> createJwtGroupDefault() {
        var tuple = JwtGroup.create();
        groupPersistence.saveJwt(tuple._1);
        endpointPersistence.saveRestful(tuple._2);
        endpointPersistence.saveRestful(tuple._3);
        return tuple;
    }
}
