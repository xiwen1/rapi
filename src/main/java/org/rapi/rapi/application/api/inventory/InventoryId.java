package org.rapi.rapi.application.api.inventory;

import java.util.UUID;

public record InventoryId(UUID id) {

    public static InventoryId create() {
        return new InventoryId(UUID.randomUUID());
    }
}
