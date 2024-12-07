package org.rapi.rapi.application.api.inventory.effectfulbehavior;

import org.rapi.rapi.application.api.inventory.Inventory;
import org.rapi.rapi.application.api.inventory.InventoryId;
import org.rapi.rapi.application.api.structure.StructureId;

public class RemoveStructureFromInventoryBehavior {

    private final InventoryPersistence inventoryPersistence;

    public RemoveStructureFromInventoryBehavior(InventoryPersistence inventoryPersistence) {
        this.inventoryPersistence = inventoryPersistence;
    }

    public void removeStructureFromInventory(InventoryId inventoryId, StructureId structureId) {
        Inventory inventory = inventoryPersistence.findById(inventoryId);
        inventory.removeStructure(structureId);
        inventoryPersistence.save(inventory);
    }

}
