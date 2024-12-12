package org.rapi.rapi.application.api.service.query;

import org.rapi.rapi.application.api.inventory.Inventory;
import org.rapi.rapi.application.api.inventory.InventoryId;
import org.rapi.rapi.application.api.service.InventoryPersistence;
import org.springframework.stereotype.Service;

@Service
public class GetInventoryByIdQuery {

    private final InventoryPersistence inventoryPersistence;

    public GetInventoryByIdQuery(InventoryPersistence inventoryPersistence) {
        this.inventoryPersistence = inventoryPersistence;
    }

    public Inventory getInventoryById(InventoryId id) {
        return inventoryPersistence.findById(id);
    }
}
