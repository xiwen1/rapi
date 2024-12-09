package org.rapi.rapi.application.api.service.command;

import io.vavr.collection.List;
import org.rapi.rapi.application.api.endpoint.RestfulEndpoint;
import org.rapi.rapi.application.api.group.GroupId;
import org.rapi.rapi.application.api.inventory.InventoryId;
import org.rapi.rapi.application.api.service.EndpointPersistence;
import org.rapi.rapi.application.api.service.GroupPersistence;
import org.rapi.rapi.application.api.service.InventoryPersistence;

public class DissolveJwtGroupCommand {

    private final GroupPersistence groupPersistence;
    private final EndpointPersistence endpointPersistence;

    private final InventoryPersistence inventoryPersistence;

    public DissolveJwtGroupCommand(GroupPersistence groupPersistence,
        EndpointPersistence endpointPersistence, InventoryPersistence inventoryPersistence) {
        this.groupPersistence = groupPersistence;
        this.endpointPersistence = endpointPersistence;
        this.inventoryPersistence = inventoryPersistence;
    }

    public List<RestfulEndpoint> dissolveJwtGroup(GroupId groupId, InventoryId inventoryId) {
        // data preparing
        var group = groupPersistence.findJwtById(groupId);
        var inventory = inventoryPersistence.findById(inventoryId);

        // operation
        var protectedEndpointIds = group.getProtectedEndpointsMap().keySet();
        var loginEndpointId = group.getLoginEndpointId();
        var refreshEndpointId = group.getRefreshEndpointId();
        var protectedEndpoints = protectedEndpointIds.map(
            endpointPersistence::findRestfulById).toList();
        var loginEndpoint = endpointPersistence.findRestfulById(loginEndpointId);
        var refreshEndpoint = endpointPersistence.findRestfulById(refreshEndpointId);
        var endpoints = group.dissolve(protectedEndpoints, loginEndpoint, refreshEndpoint);
        endpoints.toList().forEach(endpoint -> inventory.addRestfulEndpoint(endpoint.getId()));

        // saving
        groupPersistence.deleteJwt(group.getId());
        inventoryPersistence.save(inventory);
        return endpoints;
    }
}
