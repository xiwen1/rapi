package org.rapi.rapi.application.api.service.command;

import org.rapi.rapi.application.api.endpoint.RestfulEndpoint;
import org.rapi.rapi.application.api.group.Group;
import org.rapi.rapi.application.api.inventory.InventoryId;
import org.rapi.rapi.application.api.service.EndpointPersistence;
import org.rapi.rapi.application.api.service.GroupPersistence;
import org.rapi.rapi.application.api.service.InventoryPersistence;
import org.springframework.stereotype.Service;

@Service
public class UpdateRestfulEndpointCommand {

    private final EndpointPersistence endpointPersistence;
    private final GroupPersistence groupPersistence;
    private final InventoryPersistence inventoryPersistence;

    public UpdateRestfulEndpointCommand(EndpointPersistence endpointPersistence,
        GroupPersistence groupPersistence, InventoryPersistence inventoryPersistence) {
        this.endpointPersistence = endpointPersistence;
        this.groupPersistence = groupPersistence;
        this.inventoryPersistence = inventoryPersistence;
    }

    public void updateRestfulEndpoint(RestfulEndpoint restfulEndpoint, InventoryId inventoryId) {
        // data preparing
        var inventory = inventoryPersistence.findById(inventoryId);
        var inventoryGroups = inventory.getCrudGroups()
            .map(id -> (Group) groupPersistence.findCrudById(id))
            .appendAll(inventory.getJwtGroups()
                .map(id -> (Group) groupPersistence.findJwtById(id)));
        // operation
        if (inventoryGroups.exists(
            group -> group.getGeneratedEndpoints().contains(restfulEndpoint.getId()))) {
            throw new IllegalArgumentException("Cannot update generated endpoints in a group");
        }

        //saving
        endpointPersistence.saveRestful(restfulEndpoint);
    }
}
