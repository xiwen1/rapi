package org.rapi.rapi.application.api.structure;

import java.util.UUID;

public record StructureId(UUID id) {

    public static StructureId create() {
        return new StructureId(UUID.randomUUID());
    }
}
