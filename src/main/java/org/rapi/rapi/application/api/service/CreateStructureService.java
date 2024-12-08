package org.rapi.rapi.application.api.service;

import org.rapi.rapi.application.api.inventory.Inventory;
import org.rapi.rapi.application.api.inventory.InventoryId;
import org.rapi.rapi.application.api.structure.Structure;

public class CreateStructureService {

    private final StructurePersistence structurePersistence;
    private final InventoryPersistence inventoryPersistence;

    public CreateStructureService(StructurePersistence structurePersistence,
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
