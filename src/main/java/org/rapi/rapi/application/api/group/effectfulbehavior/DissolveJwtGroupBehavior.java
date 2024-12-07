package org.rapi.rapi.application.api.group.effectfulbehavior;

import io.vavr.collection.List;
import org.rapi.rapi.application.api.endpoint.RestfulEndpoint;
import org.rapi.rapi.application.api.endpoint.effectfulbehavior.EndpointPersistence;
import org.rapi.rapi.application.api.group.GroupId;

public class DissolveJwtGroupBehavior {

    private final GroupPersistence groupPersistence;
    private final EndpointPersistence endpointPersistence;

    public DissolveJwtGroupBehavior(GroupPersistence groupPersistence,
        EndpointPersistence endpointPersistence) {
        this.groupPersistence = groupPersistence;
        this.endpointPersistence = endpointPersistence;
    }

    public List<RestfulEndpoint> dissolveJwtGroup(GroupId groupId) {
        var group = groupPersistence.findJwtById(groupId);
        var protectedEndpointIds = group.getProtectedEndpointsMap().keySet();
        var loginEndpointId = group.getLoginEndpointId();
        var refreshEndpointId = group.getRefreshEndpointId();
        var protectedEndpoints = protectedEndpointIds.map(
            endpointPersistence::findRestfulById).toList();
        var loginEndpoint = endpointPersistence.findRestfulById(loginEndpointId);
        var refreshEndpoint = endpointPersistence.findRestfulById(refreshEndpointId);
        var endpoints = group.dissolve(protectedEndpoints, loginEndpoint, refreshEndpoint);
        groupPersistence.delete(group.getId());
        return endpoints;
    }
}
