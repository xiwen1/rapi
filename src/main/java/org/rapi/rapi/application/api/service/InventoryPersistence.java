package org.rapi.rapi.application.api.service;

import org.rapi.rapi.application.api.inventory.Inventory;
import org.rapi.rapi.application.api.inventory.InventoryId;

public interface InventoryPersistence {

    void save(Inventory inventory);

    void delete(InventoryId inventoryId);

    Inventory findById(InventoryId id);

}
