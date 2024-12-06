package org.rapi.rapi.application.api.group.effectfulbehavior;

import io.vavr.collection.List;
import org.rapi.rapi.application.api.endpoint.RestfulEndpoint;
import org.rapi.rapi.application.api.endpoint.effectfulbehavior.EndpointPersistence;
import org.rapi.rapi.application.api.group.GroupId;
import org.rapi.rapi.application.api.group.JwtGroup;

public class DissolveJwtGroupBehavior {

    private final GroupPersistence groupPersistence;
    private final EndpointPersistence endpointPersistence;

    public DissolveJwtGroupBehavior(GroupPersistence groupPersistence,
        EndpointPersistence endpointPersistence) {
        this.groupPersistence = groupPersistence;
        this.endpointPersistence = endpointPersistence;
    }

    public List<RestfulEndpoint> dissolveJwtGroup(GroupId groupId) {
        var group = (JwtGroup) groupPersistence.findById(groupId);
        var protectedEndpointIds = group.getProtectedEndpointsMap().keySet();
        var loginEndpointId = group.getLoginEndpointId();
        var refreshEndpointId = group.getRefreshEndpointId();
        var protectedEndpoints = protectedEndpointIds.map(
            id -> (RestfulEndpoint) endpointPersistence.findById(id)).toList();
        var loginEndpoint = (RestfulEndpoint) endpointPersistence.findById(loginEndpointId);
        var refreshEndpoint = (RestfulEndpoint) endpointPersistence.findById(refreshEndpointId);
        var endpoints = group.dissolve(protectedEndpoints, loginEndpoint, refreshEndpoint);
        groupPersistence.delete(group.getId());
        return endpoints;
    }
}
