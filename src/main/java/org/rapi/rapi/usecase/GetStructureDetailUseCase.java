package org.rapi.rapi.usecase;

import org.rapi.rapi.application.DomainIdMappingService;
import org.rapi.rapi.application.api.service.query.GetInventoryByIdQuery;
import org.rapi.rapi.application.api.service.query.GetStructureByIdQuery;
import org.rapi.rapi.application.api.structure.Structure;
import org.rapi.rapi.application.api.structure.StructureId;
import org.rapi.rapi.application.project.project.ProjectId;
import org.rapi.rapi.application.project.service.query.GetProjectByIdQuery;
import org.springframework.stereotype.Service;

@Service
public class GetStructureDetailUseCase {

    private final GetProjectByIdQuery getProjectByIdQuery;
    private final DomainIdMappingService domainIdMappingService;
    private final GetInventoryByIdQuery getInventoryByIdQuery;
    private final GetStructureByIdQuery getStructureByIdQuery;

    public GetStructureDetailUseCase(GetProjectByIdQuery getProjectByIdQuery,
        DomainIdMappingService domainIdMappingService,
        GetInventoryByIdQuery getInventoryByIdQuery, GetStructureByIdQuery getStructureByIdQuery) {
        this.getProjectByIdQuery = getProjectByIdQuery;
        this.domainIdMappingService = domainIdMappingService;
        this.getInventoryByIdQuery = getInventoryByIdQuery;
        this.getStructureByIdQuery = getStructureByIdQuery;
    }

    public Structure getStructureDetail(ProjectId projectId, StructureId structureId) {
        var project = getProjectByIdQuery.getProjectById(projectId);
        var inventoryId = domainIdMappingService.getInventoryId(project.getId());
        var structureList = getInventoryByIdQuery.getInventoryById(inventoryId).getStructures();
        if (!structureList.contains(structureId)) {
            throw new IllegalArgumentException("Structure not found");
        }
        return getStructureByIdQuery.getStructureById(structureId);
    }
}
