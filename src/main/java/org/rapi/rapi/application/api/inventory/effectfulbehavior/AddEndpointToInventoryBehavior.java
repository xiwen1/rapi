package org.rapi.rapi.application.api.inventory.effectfulbehavior;

import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.inventory.Inventory;
import org.rapi.rapi.application.api.inventory.InventoryId;

public class AddEndpointToInventoryBehavior {

    private final InventoryPersistence inventoryPersistence;

    public AddEndpointToInventoryBehavior(InventoryPersistence inventoryPersistence) {
        this.inventoryPersistence = inventoryPersistence;
    }

    public void addEndpointToInventory(InventoryId inventoryId, EndpointId endpointId) {
        Inventory inventory = inventoryPersistence.findById(inventoryId);
        inventory.addEndpoint(endpointId);
        inventoryPersistence.save(inventory);
    }
}
