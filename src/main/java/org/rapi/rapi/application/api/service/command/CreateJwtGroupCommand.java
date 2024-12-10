package org.rapi.rapi.application.api.service.command;

import io.vavr.collection.List;
import org.rapi.rapi.application.api.group.JwtGroup;
import org.rapi.rapi.application.api.inventory.InventoryId;
import org.rapi.rapi.application.api.service.EndpointPersistence;
import org.rapi.rapi.application.api.service.GroupPersistence;
import org.rapi.rapi.application.api.service.InventoryPersistence;
import org.springframework.stereotype.Service;

@Service
public class CreateJwtGroupCommand {

    private final GroupPersistence groupPersistence;
    private final InventoryPersistence inventoryPersistence;
    private final EndpointPersistence endpointPersistence;

    public CreateJwtGroupCommand(GroupPersistence groupPersistence,
        InventoryPersistence inventoryPersistence, EndpointPersistence endpointPersistence) {
        this.groupPersistence = groupPersistence;
        this.inventoryPersistence = inventoryPersistence;
        this.endpointPersistence = endpointPersistence;
    }

    public JwtGroup createJwtGroup(InventoryId inventoryId) {
        // data preparing
        var inventory = inventoryPersistence.findById(inventoryId);
        // operation
        var jwtGroupWithPreCreatedEndpoints = JwtGroup.create();
        var jwtGroup = jwtGroupWithPreCreatedEndpoints._1;
        var endpoints = List.of(jwtGroupWithPreCreatedEndpoints._2,
            jwtGroupWithPreCreatedEndpoints._3);
        inventory.addJwtGroup(jwtGroup.getId());
        // saving
        inventoryPersistence.save(inventory);
        groupPersistence.saveJwt(jwtGroup);
        endpoints.forEach(endpointPersistence::saveRestful);

        return jwtGroup;
    }
}
