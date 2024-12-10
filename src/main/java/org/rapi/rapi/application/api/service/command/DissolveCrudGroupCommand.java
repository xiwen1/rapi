package org.rapi.rapi.application.api.service.command;

import org.rapi.rapi.application.api.group.CrudGroup.CrudEndpoints;
import org.rapi.rapi.application.api.group.GroupId;
import org.rapi.rapi.application.api.inventory.InventoryId;
import org.rapi.rapi.application.api.service.EndpointPersistence;
import org.rapi.rapi.application.api.service.GroupPersistence;
import org.rapi.rapi.application.api.service.InventoryPersistence;
import org.springframework.stereotype.Service;

@Service
public class DissolveCrudGroupCommand {

    private final GroupPersistence groupPersistence;
    private final EndpointPersistence endpointPersistence;
    private final InventoryPersistence inventoryPersistence;

    public DissolveCrudGroupCommand(GroupPersistence groupPersistence,
        EndpointPersistence endpointPersistence, InventoryPersistence inventoryPersistence) {
        this.groupPersistence = groupPersistence;
        this.endpointPersistence = endpointPersistence;
        this.inventoryPersistence = inventoryPersistence;
    }

    public CrudEndpoints dissolveCrudGroup(GroupId groupId, InventoryId inventoryId) {
        // data preparing
        var group = groupPersistence.findCrudById(groupId);
        var inventory = inventoryPersistence.findById(inventoryId);
        var create = endpointPersistence.findRestfulById(group.getCreateEndpointId()
            .getOrElseThrow(() -> new IllegalStateException("Create endpoint not found")));
        var list = endpointPersistence.findRestfulById(group.getListEndpointId()
            .getOrElseThrow(() -> new IllegalStateException("List endpoint not found")));
        var update = endpointPersistence.findRestfulById(group.getUpdateEndpointId()
            .getOrElseThrow(() -> new IllegalStateException("Update endpoint not found")));
        var delete = endpointPersistence.findRestfulById(group.getDeleteEndpointId()
            .getOrElseThrow(() -> new IllegalStateException("Delete endpoint not found")));
        // operation
        var newEndpoints = group.dissolve(new CrudEndpoints(create, list, update, delete));
        newEndpoints.toList().forEach(endpoint -> inventory.addRestfulEndpoint(endpoint.getId()));
        // saving
        groupPersistence.deleteCrud(groupId);
        inventoryPersistence.save(inventory);
        return newEndpoints;
    }

}
