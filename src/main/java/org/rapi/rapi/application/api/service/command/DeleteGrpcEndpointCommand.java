package org.rapi.rapi.application.api.service.command;

import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.inventory.InventoryId;
import org.rapi.rapi.application.api.service.EndpointPersistence;
import org.rapi.rapi.application.api.service.InventoryPersistence;

public class DeleteGrpcEndpointCommand {

    private final EndpointPersistence endpointPersistence;
    private final InventoryPersistence inventoryPersistence;

    public DeleteGrpcEndpointCommand(EndpointPersistence endpointPersistence,
        InventoryPersistence inventoryPersistence) {
        this.endpointPersistence = endpointPersistence;
        this.inventoryPersistence = inventoryPersistence;
    }

    public void deleteGrpcEndpoint(InventoryId inventoryId, EndpointId endpointId) {
        // data preparing
        var inventory = inventoryPersistence.findById(inventoryId);
        // operation
        inventory.removeGrpcEndpoint(endpointId);
        // saving
        inventoryPersistence.save(inventory);
        endpointPersistence.deleteGrpc(endpointId);
    }
}
