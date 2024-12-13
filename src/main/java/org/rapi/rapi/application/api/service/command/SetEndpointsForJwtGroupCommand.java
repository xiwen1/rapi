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
import org.springframework.stereotype.Service;

@Service
public class SetEndpointsForJwtGroupCommand {

    private final EndpointPersistence endpointPersistence;
    private final GroupPersistence groupPersistence;
    private final InventoryPersistence inventoryPersistence;

    public SetEndpointsForJwtGroupCommand(EndpointPersistence endpointPersistence,
        GroupPersistence groupPersistence, InventoryPersistence inventoryPersistence) {
        this.endpointPersistence = endpointPersistence;
        this.groupPersistence = groupPersistence;
        this.inventoryPersistence = inventoryPersistence;
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

        endpointsToAdd.map(t -> t._1).forEach(inventory::removeRestfulEndpoint);
        endpointsToRemove.forEach(t -> inventory.addRestfulEndpoint(t._1));

        // saving
        endpointsToRemove.forEach(t -> endpointPersistence.deleteRestful(t._2));
        endpointsToAdd.forEach(t -> endpointPersistence.saveRestful(t._2));
        inventoryPersistence.save(inventory);
        groupPersistence.saveJwt(group);

        return new SetJwtGroupEndpointsResult(
            endpointsToAdd.map(t -> Tuple.of(t._1, t._2.getId())),
            endpointsToRemove.toList());
    }

    public record SetJwtGroupEndpointsResult(
        List<Tuple2<EndpointId, EndpointId>> removedFromInventory,
        List<Tuple2<EndpointId, EndpointId>> addedToInventory) {

    }
}
