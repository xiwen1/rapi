package org.rapi.rapi.application.api.infrastructure;

import org.rapi.rapi.application.api.infrastructure.dto.StructureDto;
import org.rapi.rapi.application.api.infrastructure.repository.StructureRepository;
import org.rapi.rapi.application.api.service.StructurePersistence;
import org.rapi.rapi.application.api.structure.Structure;
import org.rapi.rapi.application.api.structure.StructureId;

public class StructurePersistenceImpl implements StructurePersistence {

    private final StructureRepository structureRepository;

    public StructurePersistenceImpl(StructureRepository structureRepository) {
        this.structureRepository = structureRepository;
    }

    @Override
    public void save(Structure structure) {
        structureRepository.save(StructureDto.fromDomain(structure));
    }

    @Override
    public void delete(StructureId structureId) {
        structureRepository.deleteById(structureId.id().toString());
    }

    @Override
    public Structure findById(StructureId id) {
        return structureRepository.findById(id.id().toString()).orElseThrow().toDomain();
    }
}
