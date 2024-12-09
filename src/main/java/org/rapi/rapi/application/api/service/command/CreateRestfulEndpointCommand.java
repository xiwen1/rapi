package org.rapi.rapi.application.api.service.command;

import io.vavr.collection.List;
import io.vavr.control.Option;
import org.rapi.rapi.application.api.endpoint.Response;
import org.rapi.rapi.application.api.endpoint.RestfulEndpoint;
import org.rapi.rapi.application.api.endpoint.route.Route;
import org.rapi.rapi.application.api.inventory.Inventory;
import org.rapi.rapi.application.api.inventory.InventoryId;
import org.rapi.rapi.application.api.service.EndpointPersistence;
import org.rapi.rapi.application.api.service.InventoryPersistence;
import org.rapi.rapi.application.api.structure.schema.Schema;
import org.springframework.http.HttpMethod;

public class CreateRestfulEndpointCommand {

    private final EndpointPersistence endpointPersistence;
    private final InventoryPersistence inventoryPersistence;

    public CreateRestfulEndpointCommand(EndpointPersistence endpointPersistence,
        InventoryPersistence inventoryPersistence) {
        this.endpointPersistence = endpointPersistence;
        this.inventoryPersistence = inventoryPersistence;
    }

    public RestfulEndpoint createRestfulEndpoint(String title, String description,
        Option<Schema> request,
        HttpMethod method,
        Route route, List<Response> responses, InventoryId inventoryId) {
        // data preparing
        Inventory inventory = inventoryPersistence.findById(inventoryId);
        // operation
        var restfulEndpoint = RestfulEndpoint.create(title, description, request, method, route,
            responses);
        inventory.addRestfulEndpoint(restfulEndpoint.getId());
        // saving
        endpointPersistence.saveRestful(restfulEndpoint);
        inventoryPersistence.save(inventory);
        return restfulEndpoint;
    }
}
