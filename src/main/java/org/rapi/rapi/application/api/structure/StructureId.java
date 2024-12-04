package org.rapi.rapi.application.api.structure;

import java.util.UUID;

public record StructureId(UUID id) {
    public StructureId {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
    }

    public static StructureId create() {
        return new StructureId(UUID.randomUUID());
    }
}
