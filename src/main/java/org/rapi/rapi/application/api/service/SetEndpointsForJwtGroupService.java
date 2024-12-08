package org.rapi.rapi.application.api.service;

import io.vavr.collection.List;
import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.group.GroupId;

public class SetEndpointsForJwtGroupService {

    private final EndpointPersistence endpointPersistence;
    private final GroupPersistence groupPersistence;

    public SetEndpointsForJwtGroupService(EndpointPersistence endpointPersistence,
        GroupPersistence groupPersistence) {
        this.endpointPersistence = endpointPersistence;
        this.groupPersistence = groupPersistence;
    }

    public void setEndpointsForJwtGroup(List<EndpointId> newEndpointIds, GroupId groupId) {
        // data preparing
        var group = groupPersistence.findJwtById(groupId);

        // operation
        var groupSourceEndpoints = group.getProtectedEndpointsMap().keySet();
        var endpointsToRemove = groupSourceEndpoints.filter(
                endpointId -> !newEndpointIds.contains(endpointId))
            .map(group::remove);
        var endpointsToAdd = newEndpointIds.filter(
                endpointId -> !groupSourceEndpoints.contains(endpointId))
            .map(endpointPersistence::findRestfulById)
            .map(group::add);

        // saving
        endpointsToRemove.forEach(endpointPersistence::deleteRestful);
        endpointsToAdd.forEach(endpointPersistence::saveRestful);
        groupPersistence.saveJwt(group);
    }
}
