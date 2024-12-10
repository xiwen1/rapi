package org.rapi.rapi.application.api.service.command;

import org.rapi.rapi.application.api.endpoint.GrpcEndpoint;
import org.rapi.rapi.application.api.inventory.Inventory;
import org.rapi.rapi.application.api.inventory.InventoryId;
import org.rapi.rapi.application.api.service.EndpointPersistence;
import org.rapi.rapi.application.api.service.InventoryPersistence;
import org.rapi.rapi.application.api.structure.schema.Schema;
import org.springframework.stereotype.Service;

@Service
public class CreateGrpcEndpointCommand {

    private final EndpointPersistence endpointPersistence;
    private final InventoryPersistence inventoryPersistence;


    public CreateGrpcEndpointCommand(EndpointPersistence endpointPersistence,
        InventoryPersistence inventoryPersistence) {
        this.endpointPersistence = endpointPersistence;
        this.inventoryPersistence = inventoryPersistence;
    }

    public GrpcEndpoint createGrpcEndpoint(String title, String description, String service,
        boolean isParamStream, boolean isResultStream, Schema param, Schema result,
        InventoryId inventoryId) {
        // data preparing
        Inventory inventory = inventoryPersistence.findById(inventoryId);
        // operation
        var grpcEndpoint = GrpcEndpoint.create(title, description, service, isParamStream,
            isResultStream, param, result);
        inventory.addGrpcEndpoint(grpcEndpoint.getId());
        // saving
        endpointPersistence.saveGrpc(grpcEndpoint);
        inventoryPersistence.save(inventory);
        return grpcEndpoint;
    }
}
