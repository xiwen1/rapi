package org.rapi.rapi.application.api.service.command;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.group.GroupId;
import org.rapi.rapi.application.api.inventory.InventoryId;
import org.rapi.rapi.application.api.service.EndpointPersistence;
import org.rapi.rapi.application.api.service.GroupPersistence;
import org.rapi.rapi.application.api.service.InventoryPersistence;
import org.rapi.rapi.application.api.service.query.GetAllCrudGroupEndpointsQuery;
import org.springframework.stereotype.Service;

@Service
public class SetEndpointsForJwtGroupCommand {

    private final EndpointPersistence endpointPersistence;
    private final GroupPersistence groupPersistence;
    private final InventoryPersistence inventoryPersistence;
    private final GetAllCrudGroupEndpointsQuery getAllCrudGroupEndpointsQuery;

    public SetEndpointsForJwtGroupCommand(EndpointPersistence endpointPersistence,
        GroupPersistence groupPersistence, InventoryPersistence inventoryPersistence,
        GetAllCrudGroupEndpointsQuery getAllCrudGroupEndpointsQuery) {
        this.endpointPersistence = endpointPersistence;
        this.groupPersistence = groupPersistence;
        this.inventoryPersistence = inventoryPersistence;
        this.getAllCrudGroupEndpointsQuery = getAllCrudGroupEndpointsQuery;
    }

    // first is to add, seconde is to delete, third is newGenerated endpoint, fourth is new Deleted
    public SetJwtGroupEndpointsResult setEndpointsForJwtGroup(List<EndpointId> newEndpointIds,
        GroupId groupId, InventoryId inventoryId) {
        // data preparing
        var group = groupPersistence.findJwtById(groupId);
        var inventory = inventoryPersistence.findById(inventoryId);

        // operation
        var groupSourceEndpoints = group.getProtectedEndpointsMap().keySet();

        var endpointsBackToInventory = groupSourceEndpoints.filter(
            endpointId -> !newEndpointIds.contains(endpointId));
        var endpointsToRemove = endpointsBackToInventory.map(e -> Tuple.of(e, group.remove(e)));

        var endpointsRemovedFromInventory = newEndpointIds.filter(
            endpointId -> !groupSourceEndpoints.contains(endpointId));
        var endpointsToAdd = endpointsRemovedFromInventory
            .map(e -> {
                var endpoint = endpointPersistence.findRestfulById(e);
                return Tuple.of(e, group.add(endpoint));
            });

        var crudEndpoints = getAllCrudGroupEndpointsQuery.getAllCrudGroupEndpoints(inventoryId);
        endpointsToAdd.forEach(t -> {
            if (!crudEndpoints.contains(t._1)) {
                inventory.removeRestfulEndpoint(t._1);
            }
            endpointPersistence.saveRestful(t._2);

        });
        endpointsToRemove.forEach(t -> {
            if (!crudEndpoints.contains(t._1)) {
                inventory.addRestfulEndpoint(t._1);
            }
            endpointPersistence.deleteRestful(t._2);
        });
        var removedFromInventory = endpointsToAdd.map(t -> Tuple.of(t._1, t._2.getId()));
        var addedToInventory = endpointsToRemove.toList();
        // saving
        inventoryPersistence.save(inventory);
        groupPersistence.saveJwt(group);

        return new SetJwtGroupEndpointsResult(
            removedFromInventory.filter(t -> !crudEndpoints.contains(t._1)),
            addedToInventory.filter(t -> !crudEndpoints.contains(t._1)),
            removedFromInventory.filter(t -> crudEndpoints.contains(t._1)),
            addedToInventory.filter(t -> crudEndpoints.contains(t._1))
        );
    }


    public record SetJwtGroupEndpointsResult(
        List<Tuple2<EndpointId, EndpointId>> removedFromInventory,
        List<Tuple2<EndpointId, EndpointId>> addedToInventory,
        List<Tuple2<EndpointId, EndpointId>> getFromCrud,
        List<Tuple2<EndpointId, EndpointId>> backToCrud
    ) {

    }
}
