package org.rapi.rapi.application.api.service.query;

import io.vavr.collection.List;
import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.group.CrudGroup;
import org.rapi.rapi.application.api.inventory.InventoryId;
import org.rapi.rapi.application.api.service.GroupPersistence;
import org.rapi.rapi.application.api.service.InventoryPersistence;
import org.springframework.stereotype.Service;

@Service
public class GetAllCrudGroupEndpointsQuery {


    private final InventoryPersistence inventoryPersistence;
    private final GroupPersistence groupPersistence;

    public GetAllCrudGroupEndpointsQuery(InventoryPersistence inventoryPersistence,
        GroupPersistence groupPersistence) {
        this.inventoryPersistence = inventoryPersistence;
        this.groupPersistence = groupPersistence;
    }

    public List<EndpointId> getAllCrudGroupEndpoints(InventoryId inventoryId) {
        var inventory = inventoryPersistence.findById(inventoryId);
        var crudGroups = inventory.getCrudGroups().map(groupPersistence::findCrudById);
        return crudGroups.flatMap(CrudGroup::getGeneratedEndpoints);
    }
}
