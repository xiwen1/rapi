package org.rapi.rapi.application.api.structure.effectfulbehavior;

import org.rapi.rapi.application.api.structure.Structure;
import org.rapi.rapi.application.api.structure.StructureId;

public interface StructurePersistence {

    void save(Structure structure);

    void delete(StructureId structureId);

    Structure findById(StructureId id);
}
