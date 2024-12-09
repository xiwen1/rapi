package org.rapi.rapi.application.api.service.command;

import org.rapi.rapi.application.api.inventory.Inventory;
import org.rapi.rapi.application.api.inventory.InventoryId;
import org.rapi.rapi.application.api.service.InventoryPersistence;
import org.rapi.rapi.application.api.service.StructurePersistence;
import org.rapi.rapi.application.api.structure.Structure;

public class CreateStructureCommand {

    private final StructurePersistence structurePersistence;
    private final InventoryPersistence inventoryPersistence;

    public CreateStructureCommand(StructurePersistence structurePersistence,
        InventoryPersistence inventoryPersistence) {
        this.structurePersistence = structurePersistence;
        this.inventoryPersistence = inventoryPersistence;
    }

    public Structure createStructureInInventory(InventoryId inventoryId) {
        // data preparing
        Inventory inventory = inventoryPersistence.findById(inventoryId);
        // operation
        Structure structure = Structure.create();
        inventory.addStructure(structure.getId());
        // saving
        structurePersistence.save(structure);
        inventoryPersistence.save(inventory);
        return structure;
    }
}
