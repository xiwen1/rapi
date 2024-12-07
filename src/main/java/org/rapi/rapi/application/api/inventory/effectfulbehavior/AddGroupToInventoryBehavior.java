package org.rapi.rapi.application.api.inventory.effectfulbehavior;

import org.rapi.rapi.application.api.group.GroupId;
import org.rapi.rapi.application.api.inventory.Inventory;
import org.rapi.rapi.application.api.inventory.InventoryId;

public class AddGroupToInventoryBehavior {

    private final InventoryPersistence inventoryPersistence;

    public AddGroupToInventoryBehavior(InventoryPersistence inventoryPersistence) {
        this.inventoryPersistence = inventoryPersistence;
    }

    public void addGroupToInventory(InventoryId inventoryId, GroupId groupId) {
        Inventory inventory = inventoryPersistence.findById(inventoryId);
        inventory.addGroup(groupId);
        inventoryPersistence.save(inventory);
    }
}
