package org.rapi.rapi.application.api.service.query;

import io.vavr.collection.List;
import org.rapi.rapi.application.api.inventory.InventoryId;
import org.rapi.rapi.application.api.service.InventoryPersistence;
import org.rapi.rapi.application.api.service.StructurePersistence;
import org.rapi.rapi.application.api.structure.Structure;
import org.springframework.stereotype.Service;

@Service
public class GetStructureListQuery {

    private final StructurePersistence structurePersistence;
    private final InventoryPersistence inventoryPersistence;

    public GetStructureListQuery(StructurePersistence structurePersistence,
        InventoryPersistence inventoryPersistence) {
        this.structurePersistence = structurePersistence;
        this.inventoryPersistence = inventoryPersistence;
    }

    public List<Structure> getStructureList(InventoryId inventoryId) {
        return inventoryPersistence.findById(inventoryId).getStructures()
            .map(structurePersistence::findById);
    }
}
