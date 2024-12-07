package org.rapi.rapi.application.api.structure.effectfulbehavior;

import org.rapi.rapi.application.api.structure.Structure;

public class UpdateStructureBehavior {

    private final StructurePersistence structurePersistence;

    public UpdateStructureBehavior(StructurePersistence structurePersistence) {
        this.structurePersistence = structurePersistence;
    }

    public void updateStructure(Structure structure) {
        structurePersistence.save(structure);
    }
}
