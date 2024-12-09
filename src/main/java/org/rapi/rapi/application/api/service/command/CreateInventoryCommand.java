package org.rapi.rapi.application.api.service.command;

import org.rapi.rapi.application.api.inventory.Inventory;
import org.rapi.rapi.application.api.service.InventoryPersistence;

public class CreateInventoryCommand {

    private final InventoryPersistence inventoryPersistence;

    public CreateInventoryCommand(InventoryPersistence inventoryPersistence) {
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
