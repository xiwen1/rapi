package org.rapi.rapi.application.api.service.command;

import io.vavr.collection.List;
import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.inventory.InventoryId;
import org.rapi.rapi.application.api.service.EndpointPersistence;
import org.rapi.rapi.application.api.service.GroupPersistence;
import org.rapi.rapi.application.api.service.InventoryPersistence;
import org.rapi.rapi.application.api.service.StructurePersistence;
import org.rapi.rapi.application.api.structure.StructureId;
import org.springframework.stereotype.Service;

@Service
public class DeleteStructureCommand {

    private final StructurePersistence structurePersistence;
    private final InventoryPersistence inventoryPersistence;
    private final EndpointPersistence endpointPersistence;
    private final GroupPersistence groupPersistence;


    public DeleteStructureCommand(StructurePersistence structurePersistence,
        InventoryPersistence inventoryPersistence, EndpointPersistence endpointPersistence,
        GroupPersistence groupPersistence) {
        this.structurePersistence = structurePersistence;
        this.inventoryPersistence = inventoryPersistence;
        this.endpointPersistence = endpointPersistence;
        this.groupPersistence = groupPersistence;
    }

    public List<EndpointId> deleteStructure(InventoryId inventoryId, StructureId structureId) {
        // data preparing
        var inventory = inventoryPersistence.findById(inventoryId);
        var crudGroups = inventory.getCrudGroups().map(groupPersistence::findCrudById);
        var jwtGroups = inventory.getJwtGroups().map(groupPersistence::findJwtById);
        // operation
        inventory.removeStructure(structureId);
        var relatedCrudGroups = crudGroups
            .filter(group -> group.getSource().isDefined())
            .filter(group -> group.getSource().get().equals(structureId));
        var crudEndpointsToDelete = relatedCrudGroups.map(group -> group.reset().toList())
            .flatMap(list -> list);
        var jwtEndpointsToDelete = jwtGroups.map(group ->
                group.getProtectedEndpointsMap().keySet().toList()
                    .filter(crudEndpointsToDelete::contains).map(group::remove))
            .flatMap(list -> list);
        // saving
        jwtEndpointsToDelete.forEach(endpointPersistence::deleteRestful);
        crudEndpointsToDelete.forEach(endpointPersistence::deleteRestful);
        crudGroups.forEach(groupPersistence::saveCrud);
        jwtGroups.forEach(groupPersistence::saveJwt);
        structurePersistence.delete(structureId);
        inventoryPersistence.save(inventory);
        return jwtEndpointsToDelete.appendAll(crudEndpointsToDelete);
    }
}
