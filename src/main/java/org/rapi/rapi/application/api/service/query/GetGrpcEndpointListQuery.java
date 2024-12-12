package org.rapi.rapi.application.api.service.query;

import io.vavr.collection.List;
import org.rapi.rapi.application.api.endpoint.GrpcEndpoint;
import org.rapi.rapi.application.api.infrastructure.EndpointPersistenceImpl;
import org.rapi.rapi.application.api.infrastructure.InventoryPersistenceImpl;
import org.rapi.rapi.application.api.inventory.InventoryId;
import org.springframework.stereotype.Service;

@Service
public class GetGrpcEndpointListQuery {

    private final InventoryPersistenceImpl inventoryPersistence;
    private final EndpointPersistenceImpl endpointPersistence;

    public GetGrpcEndpointListQuery(InventoryPersistenceImpl inventoryPersistence,
        EndpointPersistenceImpl endpointPersistence) {
        this.inventoryPersistence = inventoryPersistence;
        this.endpointPersistence = endpointPersistence;
    }

    public List<GrpcEndpoint> getGrpcEndpoints(InventoryId inventoryId) {
        return inventoryPersistence.findById(inventoryId).getGrpcEndpoints()
            .map(endpointPersistence::findGrpcById);
    }
}
