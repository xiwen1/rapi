package org.rapi.rapi.application.api.structure.schema;

import org.rapi.rapi.application.api.structure.StructureId;

public record RefSchema(StructureId reference, Transformation transformation) implements Schema {

}
