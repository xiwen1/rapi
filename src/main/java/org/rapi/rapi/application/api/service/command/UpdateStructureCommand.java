package org.rapi.rapi.application.api.service.command;

import org.rapi.rapi.application.api.endpoint.RestfulEndpoint;
import org.rapi.rapi.application.api.inventory.InventoryId;
import org.rapi.rapi.application.api.service.EndpointPersistence;
import org.rapi.rapi.application.api.service.GroupPersistence;
import org.rapi.rapi.application.api.service.InventoryPersistence;
import org.rapi.rapi.application.api.service.StructurePersistence;
import org.rapi.rapi.application.api.structure.Structure;

public class UpdateStructureCommand {

    private final StructurePersistence structurePersistence;
    private final InventoryPersistence inventoryPersistence;
    private final GroupPersistence groupPersistence;
    private final EndpointPersistence endpointPersistence;

    public UpdateStructureCommand(StructurePersistence structurePersistence,
        InventoryPersistence inventoryPersistence, GroupPersistence groupPersistence,
        EndpointPersistence endpointPersistence) {
        this.structurePersistence = structurePersistence;
        this.inventoryPersistence = inventoryPersistence;
        this.groupPersistence = groupPersistence;
        this.endpointPersistence = endpointPersistence;
    }

    public void updateStructure(Structure structure, InventoryId inventoryId) {
        // data preparing
        var inventory = inventoryPersistence.findById(inventoryId);
        var crudGroups = inventory.getCrudGroups().map(groupPersistence::findCrudById);
        var jwtGroups = inventory.getJwtGroups().map(groupPersistence::findJwtById);

        // operation
        var relatedCrudEndpoints = crudGroups.filter(
                group -> group.getSource().contains(structure.getId()))
            .map(group -> group.regenerate(structure).toList())
            .flatMap(list -> list);

        var endpointIds = relatedCrudEndpoints.map(RestfulEndpoint::getId);
        var relatedJwtGroups = jwtGroups.filter(
            group -> group.getProtectedEndpointsMap().keySet().exists(endpointIds::contains));

        // saving
        relatedCrudEndpoints.forEach(endpointPersistence::saveRestful);
        structurePersistence.save(structure);

        // operation
        relatedJwtGroups.forEach(group -> {
            var endpoints = group.getProtectedEndpointsMap().keySet()
                .map(endpointPersistence::findRestfulById);
            group.regenerate(endpoints.toList()).forEach(endpointPersistence::saveRestful);
        });
    }
}
