package org.rapi.rapi.application.api.inventory.effectfulbehavior;

import org.rapi.rapi.application.api.inventory.Inventory;

public class CreateInventoryBehavior {

    private final InventoryPersistence inventoryPersistence;

    public CreateInventoryBehavior(InventoryPersistence inventoryPersistence) {
        this.inventoryPersistence = inventoryPersistence;
    }

    public Inventory createInventory() {
        Inventory inventory = Inventory.create();
        inventoryPersistence.save(inventory);
        return inventory;
    }
}
