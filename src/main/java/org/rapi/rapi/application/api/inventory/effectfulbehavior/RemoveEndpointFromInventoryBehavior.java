package org.rapi.rapi.application.api.inventory.effectfulbehavior;

import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.inventory.Inventory;
import org.rapi.rapi.application.api.inventory.InventoryId;

public class RemoveEndpointFromInventoryBehavior {

    private final InventoryPersistence inventoryPersistence;

    public RemoveEndpointFromInventoryBehavior(InventoryPersistence inventoryPersistence) {
        this.inventoryPersistence = inventoryPersistence;
    }

    public void removeEndpointFromInventory(InventoryId inventoryId, EndpointId endpointId) {
        Inventory inventory = inventoryPersistence.findById(inventoryId);
        inventory.removeEndpoint(endpointId);
        inventoryPersistence.save(inventory);
    }
}
