package org.rapi.rapi.application.api.group.effectfulbehavior;

import io.vavr.Tuple2;
import io.vavr.collection.List;
import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.endpoint.RestfulEndpoint;
import org.rapi.rapi.application.api.endpoint.effectfulbehavior.EndpointPersistence;
import org.rapi.rapi.application.api.group.GroupId;
import org.rapi.rapi.application.api.group.JwtGroup;

public class RegenerateJwtGroupBehavior {

    private final GroupPersistence groupPersistence;
    private final EndpointPersistence endpointPersistence;

    public RegenerateJwtGroupBehavior(GroupPersistence groupPersistence,
        EndpointPersistence endpointPersistence) {
        this.groupPersistence = groupPersistence;
        this.endpointPersistence = endpointPersistence;
    }

    public Tuple2<JwtGroup, List<RestfulEndpoint>> regenerateJwtGroup(GroupId groupId,
        List<EndpointId> endpointIds) {
        var endpoints = endpointIds.map(id -> (RestfulEndpoint) endpointPersistence.findById(id));
        var group = (JwtGroup) groupPersistence.findById(groupId);
        var newEndpoints = group.regenerate(endpoints);
        newEndpoints.forEach(endpointPersistence::saveRestful);
        return new Tuple2<>(group, newEndpoints);
    }
}
