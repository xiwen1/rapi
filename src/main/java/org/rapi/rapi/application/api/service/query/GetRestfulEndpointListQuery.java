package org.rapi.rapi.application.api.service.query;

import io.vavr.collection.List;
import org.rapi.rapi.application.api.endpoint.RestfulEndpoint;
import org.rapi.rapi.application.api.inventory.InventoryId;
import org.rapi.rapi.application.api.service.EndpointPersistence;
import org.rapi.rapi.application.api.service.InventoryPersistence;
import org.springframework.stereotype.Service;

@Service
public class GetRestfulEndpointListQuery {

    private final InventoryPersistence inventoryPersistence;
    private final EndpointPersistence endpointPersistence;

    public GetRestfulEndpointListQuery(InventoryPersistence inventoryPersistence,
        EndpointPersistence endpointPersistence) {
        this.inventoryPersistence = inventoryPersistence;
        this.endpointPersistence = endpointPersistence;
    }

    public List<RestfulEndpoint> getRestfulEndpointList(InventoryId inventoryId) {
        return inventoryPersistence.findById(inventoryId).getRestfulEndpoints()
            .map(endpointPersistence::findRestfulById);
    }
}
