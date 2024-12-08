package org.rapi.rapi.application.api.service;

import org.rapi.rapi.application.api.inventory.Inventory;

public class CreateInventoryService {

    private final InventoryPersistence inventoryPersistence;

    public CreateInventoryService(InventoryPersistence inventoryPersistence) {
        this.inventoryPersistence = inventoryPersistence;
    }

    public Inventory createInventory() {
        // operation
        Inventory inventory = Inventory.create();
        //saving
        inventoryPersistence.save(inventory);
        return inventory;
    }
}
