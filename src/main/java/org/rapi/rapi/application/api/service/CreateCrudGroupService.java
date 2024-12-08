package org.rapi.rapi.application.api.service;

import org.rapi.rapi.application.api.group.CrudGroup;
import org.rapi.rapi.application.api.inventory.InventoryId;

public class CreateCrudGroupService {

    private final GroupPersistence groupPersistence;
    private final InventoryPersistence inventoryPersistence;

    public CreateCrudGroupService(GroupPersistence groupPersistence,
        InventoryPersistence inventoryPersistence) {
        this.groupPersistence = groupPersistence;
        this.inventoryPersistence = inventoryPersistence;
    }

    public CrudGroup createCrudGroup(InventoryId inventoryId) {
        // data preparing
        var inventory = inventoryPersistence.findById(inventoryId);
        //operation
        var group = CrudGroup.create();
        inventory.addCrudGroup(group.getId());
        // saving
        groupPersistence.saveCrud(group);
        inventoryPersistence.save(inventory);
        return group;
    }


}
