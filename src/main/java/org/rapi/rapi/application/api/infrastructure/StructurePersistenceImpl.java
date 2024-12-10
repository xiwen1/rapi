package org.rapi.rapi.application.api.infrastructure;

import org.rapi.rapi.application.api.infrastructure.mapping.ApiMappingService;
import org.rapi.rapi.application.api.infrastructure.repository.StructureRepository;
import org.rapi.rapi.application.api.service.StructurePersistence;
import org.rapi.rapi.application.api.structure.Structure;
import org.rapi.rapi.application.api.structure.StructureId;
import org.springframework.stereotype.Service;

@Service
public class StructurePersistenceImpl implements StructurePersistence {

    private final StructureRepository structureRepository;
    private final ApiMappingService apiMappingService;

    public StructurePersistenceImpl(StructureRepository structureRepository,
        ApiMappingService apiMappingService) {
        this.structureRepository = structureRepository;
        this.apiMappingService = apiMappingService;
    }

    @Override
    public void save(Structure structure) {
        structureRepository.save(apiMappingService.toStructureDto(structure));
    }

    @Override
    public void delete(StructureId structureId) {
        structureRepository.deleteById(structureId.id().toString());
    }

    @Override
    public Structure findById(StructureId id) {
        return apiMappingService.fromStructureDto(
            structureRepository.findById(id.id().toString()).orElseThrow());
    }
}
