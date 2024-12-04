package org.rapi.rapi.application.api.structure.schema;

import org.rapi.rapi.application.api.structure.StructureId;

public record RefSchema(StructureId reference, Transformation transformation) implements Schema {
    public RefSchema {
        if (reference == null) {
            throw new IllegalArgumentException("Reference cannot be null");
        }
        if (transformation == null) {
            throw new IllegalArgumentException("Transformation cannot be null");
        }
    }
}
