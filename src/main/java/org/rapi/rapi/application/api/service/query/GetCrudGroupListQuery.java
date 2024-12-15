package org.rapi.rapi.application.api.service.query;

import io.vavr.collection.List;
import org.rapi.rapi.application.api.group.CrudGroup;
import org.rapi.rapi.application.api.inventory.InventoryId;
import org.rapi.rapi.application.api.service.GroupPersistence;
import org.rapi.rapi.application.api.service.InventoryPersistence;
import org.springframework.stereotype.Service;

@Service
public class GetCrudGroupListQuery {

    private final InventoryPersistence inventoryPersistence;
    private final GroupPersistence groupPersistence;

    public GetCrudGroupListQuery(InventoryPersistence inventoryPersistence,
        GroupPersistence groupPersistence) {
        this.inventoryPersistence = inventoryPersistence;
        this.groupPersistence = groupPersistence;
    }

    public List<CrudGroup> getCrudGroupList(InventoryId inventoryId) {
        return inventoryPersistence.findById(inventoryId).getCrudGroups()
            .map(groupPersistence::findCrudById);
    }
}
