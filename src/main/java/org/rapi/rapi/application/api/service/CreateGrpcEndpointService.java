package org.rapi.rapi.application.api.service;

import org.rapi.rapi.application.api.endpoint.GrpcEndpoint;
import org.rapi.rapi.application.api.inventory.Inventory;
import org.rapi.rapi.application.api.inventory.InventoryId;
import org.rapi.rapi.application.api.structure.schema.Schema;

public class CreateGrpcEndpointService {

    private final EndpointPersistence endpointPersistence;
    private final InventoryPersistence inventoryPersistence;


    public CreateGrpcEndpointService(EndpointPersistence endpointPersistence,
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
