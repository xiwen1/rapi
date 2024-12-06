package org.rapi.rapi.application.api.structure.effectfulbehavior;

import org.rapi.rapi.application.api.structure.Structure;

public class CreateStructureBehavior {

    private final StructurePersistence structurePersistence;

    public CreateStructureBehavior(StructurePersistence structurePersistence) {
        this.structurePersistence = structurePersistence;
    }

    public Structure createStructure() {
        Structure structure = Structure.create();
        structurePersistence.save(structure);
        return structure;
    }
}
