package org.rapi.rapi.application.api.service.query;

import io.vavr.collection.List;
import org.rapi.rapi.application.api.group.JwtGroup;
import org.rapi.rapi.application.api.inventory.InventoryId;
import org.rapi.rapi.application.api.service.GroupPersistence;
import org.rapi.rapi.application.api.service.InventoryPersistence;
import org.springframework.stereotype.Service;

@Service
public class GetJwtGroupListQuery {

    private final InventoryPersistence inventoryPersistence;
    private final GroupPersistence groupPersistence;

    public GetJwtGroupListQuery(InventoryPersistence inventoryPersistence,
        GroupPersistence groupPersistence) {
        this.inventoryPersistence = inventoryPersistence;
        this.groupPersistence = groupPersistence;
    }

    public List<JwtGroup> getJwtGroupList(InventoryId inventoryId) {
        return inventoryPersistence.findById(inventoryId).getJwtGroups()
            .map(groupPersistence::findJwtById);
    }
}
