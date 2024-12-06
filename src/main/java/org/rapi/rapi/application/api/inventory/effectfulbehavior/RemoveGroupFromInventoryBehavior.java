package org.rapi.rapi.application.api.inventory.effectfulbehavior;

import org.rapi.rapi.application.api.group.GroupId;
import org.rapi.rapi.application.api.inventory.Inventory;
import org.rapi.rapi.application.api.inventory.InventoryId;

public class RemoveGroupFromInventoryBehavior {

    private final InventoryPersistence inventoryPersistence;

    public RemoveGroupFromInventoryBehavior(InventoryPersistence inventoryPersistence) {
        this.inventoryPersistence = inventoryPersistence;
    }

    public void removeGroupFromInventory(InventoryId inventoryId, GroupId groupId) {
        Inventory inventory = inventoryPersistence.findById(inventoryId);
        inventory.removeGroup(groupId);
        inventoryPersistence.save(inventory);
    }
}
