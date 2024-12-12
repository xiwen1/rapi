package org.rapi.rapi.presentation.controller;


import java.util.List;
import java.util.UUID;
import org.rapi.rapi.application.DomainIdMappingService;
import org.rapi.rapi.application.api.service.query.GetStructureListQuery;
import org.rapi.rapi.application.api.structure.Structure;
import org.rapi.rapi.application.api.structure.StructureId;
import org.rapi.rapi.application.auth.user.User;
import org.rapi.rapi.application.project.project.ProjectId;
import org.rapi.rapi.application.project.service.query.GetProjectByIdQuery;
import org.rapi.rapi.presentation.converter.PresentationSchemaConverter;
import org.rapi.rapi.presentation.dto.SchemaDto;
import org.rapi.rapi.usecase.CreateStructureUseCase;
import org.rapi.rapi.usecase.DeleteStructureUseCase;
import org.rapi.rapi.usecase.GetStructureDetailUseCase;
import org.rapi.rapi.usecase.UpdateStructureUseCase;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/project/{project_id}/structure")
public class StructureController {

    private final GetProjectByIdQuery getProjectByIdQuery;
    private final DomainIdMappingService domainIdMappingService;
    private final GetStructureListQuery getStructureListQuery;
    private final GetStructureDetailUseCase getStructureDetailUseCase;
    private final PresentationSchemaConverter presentationSchemaConverter;
    private final CreateStructureUseCase createStructureUseCase;
    private final DeleteStructureUseCase deleteStructureUseCase;
    private final UpdateStructureUseCase updateStructureUseCase;

    public StructureController(GetProjectByIdQuery getProjectByIdQuery,
        DomainIdMappingService domainIdMappingService, GetStructureListQuery getStructureListQuery,
        GetStructureDetailUseCase getStructureDetailUseCase,
        PresentationSchemaConverter presentationSchemaConverter,
        CreateStructureUseCase createStructureUseCase,
        DeleteStructureUseCase deleteStructureUseCase,
        UpdateStructureUseCase updateStructureUseCase) {
        this.getProjectByIdQuery = getProjectByIdQuery;
        this.domainIdMappingService = domainIdMappingService;
        this.getStructureListQuery = getStructureListQuery;
        this.getStructureDetailUseCase = getStructureDetailUseCase;
        this.presentationSchemaConverter = presentationSchemaConverter;
        this.createStructureUseCase = createStructureUseCase;
        this.deleteStructureUseCase = deleteStructureUseCase;
        this.updateStructureUseCase = updateStructureUseCase;
    }

    private static User getUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @GetMapping("/{structure_id}")
    public GetStructureDetailResponse getStructureDetail(
        @PathVariable("project_id") String projectId,
        @PathVariable("structure_id") String structureId) {
        var structure = getStructureDetailUseCase.getStructureDetail(
            new ProjectId(UUID.fromString(projectId)),
            new StructureId(UUID.fromString(structureId)));
        return new GetStructureDetailResponse(structure.getId().toString(), structure.getName(),
            presentationSchemaConverter.toSchemaDto(structure.getSchema()));
    }

    @GetMapping("/")
    public List<GetStructureListResponseItem> getStructureList(
        @PathVariable("project_id") String projectId) {
        var project = getProjectByIdQuery.getProjectById(new ProjectId(UUID.fromString(projectId)));
        var inventoryId = domainIdMappingService.getInventoryId(project.getId());
        var structureList = getStructureListQuery.getStructureList(inventoryId);
        return structureList.map(s -> new GetStructureListResponseItem(
            s.getId().toString(), s.getName())).toJavaList();
    }

    @PostMapping("/")
    public CreateStructureResponse createStructure(@PathVariable("project_id") String projectId,
        CreateStructureRequest request) {
        var user = getUser();
        return new CreateStructureResponse(createStructureUseCase.createStructure(request.name(),
            new ProjectId(UUID.fromString(projectId)), user.getId()).id().toString());
    }

    @DeleteMapping("/{structure_id}")
    public void deleteStructure(@PathVariable("project_id") String projectId,
        @PathVariable("structure_id") String structureId) {
        var user = getUser();
        deleteStructureUseCase.deleteStructure(new ProjectId(UUID.fromString(projectId)),
            user.getId(),
            new StructureId(UUID.fromString(structureId)));
    }

    @PutMapping("/{structure_id}")
    public void updateStructure(@PathVariable("project_id") String projectId,
        @PathVariable("structure_id") String structureId, UpdateStructureRequest request) {
        var user = getUser();
        var schema = presentationSchemaConverter.fromSchemaDto(request.schemaDto());
        var structure = Structure.fromRaw(new StructureId(UUID.fromString(structureId)), schema,
            request.name());
        updateStructureUseCase.updateStructure(new ProjectId(UUID.fromString(projectId)),
            user.getId(), structure);
    }

    public record GetStructureDetailResponse(String id, String name, SchemaDto schema) {

    }

    public record GetStructureListResponseItem(String id, String name) {

    }

    public record CreateStructureRequest(String name) {

    }

    public record CreateStructureResponse(String id) {

    }

    public record UpdateStructureRequest(String id, String name, SchemaDto schemaDto) {

    }

    public record UpdateStructureResponse(String id) {

    }


}
