package org.rapi.rapi.application.api.service.command;

import io.vavr.collection.List;
import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.group.GroupId;
import org.rapi.rapi.application.api.inventory.InventoryId;
import org.rapi.rapi.application.api.service.EndpointPersistence;
import org.rapi.rapi.application.api.service.GroupPersistence;
import org.rapi.rapi.application.api.service.InventoryPersistence;
import org.rapi.rapi.application.api.service.StructurePersistence;
import org.rapi.rapi.application.api.structure.StructureId;
import org.springframework.stereotype.Service;

@Service
public class SetStructureForCrudGroupCommand {

    private final GroupPersistence groupPersistence;
    private final EndpointPersistence endpointPersistence;
    private final StructurePersistence structurePersistence;
    private final InventoryPersistence inventoryPersistence;

    public SetStructureForCrudGroupCommand(GroupPersistence groupPersistence,
        EndpointPersistence endpointPersistence, StructurePersistence structurePersistence,
        InventoryPersistence inventoryPersistence) {
        this.groupPersistence = groupPersistence;
        this.endpointPersistence = endpointPersistence;
        this.structurePersistence = structurePersistence;
        this.inventoryPersistence = inventoryPersistence;
    }

    public List<EndpointId> setStructureForCrudGroup(GroupId groupId, StructureId structureId,
        InventoryId inventoryId) {
        // data preparing
        var structure = structurePersistence.findById(structureId);
        var crudGroup = groupPersistence.findCrudById(groupId);
        var jwtGroups = inventoryPersistence.findById(inventoryId).getJwtGroups()
            .map(groupPersistence::findJwtById);

        // operation
        if (crudGroup.getSource().isDefined()) {
            if (structureId.equals(crudGroup.getSource().get())) {
                throw new IllegalArgumentException(
                    "Cannot set the source structure as the CRUD group structure");
            }

            var crudEndpointsToDelete = crudGroup.reset().toList();
            var jwtEndpointToDelete = jwtGroups.map(group -> crudEndpointsToDelete.filter(
                    group.getProtectedEndpointsMap().keySet()::contains)
                .map(group::remove)).flatMap(list -> list);
            // save
            crudEndpointsToDelete.forEach(endpointPersistence::deleteRestful);
            jwtEndpointToDelete.forEach(endpointPersistence::deleteRestful);
        }
        // save
        crudGroup.set(structure).toList().forEach(endpointPersistence::saveRestful);
        groupPersistence.saveCrud(crudGroup);
        jwtGroups.forEach(groupPersistence::saveJwt);
        return crudGroup.getGeneratedEndpoints();
    }
}
