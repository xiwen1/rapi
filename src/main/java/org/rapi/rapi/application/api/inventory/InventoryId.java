package org.rapi.rapi.application.api.inventory;

import java.util.UUID;

public record InventoryId(UUID id) {
    public InventoryId {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
    }

    public static InventoryId create() {
        return new InventoryId(UUID.randomUUID());
    }
}
