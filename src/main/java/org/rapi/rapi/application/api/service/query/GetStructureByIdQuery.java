package org.rapi.rapi.application.api.service.query;

import org.rapi.rapi.application.api.service.StructurePersistence;
import org.rapi.rapi.application.api.structure.Structure;
import org.rapi.rapi.application.api.structure.StructureId;
import org.springframework.stereotype.Service;

@Service
public class GetStructureByIdQuery {

    private final StructurePersistence structurePersistence;

    public GetStructureByIdQuery(StructurePersistence structurePersistence) {
        this.structurePersistence = structurePersistence;
    }

    public Structure getStructureById(StructureId structureId) {
        return structurePersistence.findById(structureId);
    }
}
