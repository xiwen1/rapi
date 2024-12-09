package org.rapi.rapi.application.api.service.command;

import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.inventory.InventoryId;
import org.rapi.rapi.application.api.service.EndpointPersistence;
import org.rapi.rapi.application.api.service.GroupPersistence;
import org.rapi.rapi.application.api.service.InventoryPersistence;

public class DeleteRestfulEndpointCommand {

    private final InventoryPersistence inventoryPersistence;
    private final EndpointPersistence endpointPersistence;
    private final GroupPersistence groupPersistence;

    public DeleteRestfulEndpointCommand(InventoryPersistence inventoryPersistence,
        EndpointPersistence endpointPersistence, GroupPersistence groupPersistence) {
        this.inventoryPersistence = inventoryPersistence;
        this.endpointPersistence = endpointPersistence;
        this.groupPersistence = groupPersistence;
    }

    public void deleteRestfulEndpoint(InventoryId inventoryId, EndpointId endpointId) {
        // data preparing
        var inventory = inventoryPersistence.findById(inventoryId);
        var crudGroups = inventory.getCrudGroups().map(groupPersistence::findCrudById);
        var jwtGroups = inventory.getJwtGroups().map(groupPersistence::findJwtById);
        // operation
        crudGroups.forEach(group -> {
            if (group.getGeneratedEndpoints().contains(endpointId)) {
                throw new IllegalArgumentException(
                    "Cannot delete endpoint that is part of a generated group");
            }
        });

        jwtGroups.forEach(group -> {
            if (group.getGeneratedEndpoints().contains(endpointId)) {
                throw new IllegalArgumentException(
                    "Cannot delete endpoint that is part of a generated group");
            }
        });
        inventory.removeRestfulEndpoint(endpointId);

        jwtGroups.filter(group -> group.getProtectedEndpointsMap().keySet().contains(endpointId))
            .forEach(group -> {
                // saving
                endpointPersistence.deleteRestful(group.remove(endpointId));
                groupPersistence.saveJwt(group);
            });
        // saving
        inventoryPersistence.save(inventory);
        endpointPersistence.deleteRestful(endpointId);

    }
}
