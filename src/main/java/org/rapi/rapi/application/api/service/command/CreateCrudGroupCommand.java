package org.rapi.rapi.application.api.service.command;

import org.rapi.rapi.application.api.group.CrudGroup;
import org.rapi.rapi.application.api.inventory.InventoryId;
import org.rapi.rapi.application.api.service.GroupPersistence;
import org.rapi.rapi.application.api.service.InventoryPersistence;
import org.springframework.stereotype.Service;

@Service
public class CreateCrudGroupCommand {

    private final GroupPersistence groupPersistence;
    private final InventoryPersistence inventoryPersistence;

    public CreateCrudGroupCommand(GroupPersistence groupPersistence,
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
