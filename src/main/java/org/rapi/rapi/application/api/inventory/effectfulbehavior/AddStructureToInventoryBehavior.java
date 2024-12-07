package org.rapi.rapi.application.api.inventory.effectfulbehavior;

import org.rapi.rapi.application.api.inventory.Inventory;
import org.rapi.rapi.application.api.inventory.InventoryId;
import org.rapi.rapi.application.api.structure.StructureId;

public class AddStructureToInventoryBehavior {

    private final InventoryPersistence inventoryPersistence;

    public AddStructureToInventoryBehavior(InventoryPersistence inventoryPersistence) {
        this.inventoryPersistence = inventoryPersistence;
    }

    public void addStructureToInventory(InventoryId inventoryId, StructureId structureId) {
        Inventory inventory = inventoryPersistence.findById(inventoryId);
        inventory.addStructure(structureId);
        inventoryPersistence.save(inventory);
    }
}
