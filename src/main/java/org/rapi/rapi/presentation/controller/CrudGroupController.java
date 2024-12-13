package org.rapi.rapi.presentation.controller;

import org.rapi.rapi.application.AuthorizeUserAccessInProjectService;
import org.rapi.rapi.application.api.group.GroupId;
import org.rapi.rapi.application.api.infrastructure.mapping.UuidConverter;
import org.rapi.rapi.application.api.service.GroupPersistence;
import org.rapi.rapi.application.api.service.query.GetStructureByIdQuery;
import org.rapi.rapi.application.api.structure.StructureId;
import org.rapi.rapi.application.project.project.ProjectId;
import org.rapi.rapi.presentation.GetCurrentUserService;
import org.rapi.rapi.usecase.CreateCrudGroupUseCase;
import org.rapi.rapi.usecase.DissolveCrudGroupUseCase;
import org.rapi.rapi.usecase.SetStructureForCrudGroupUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/project/{project_id}/crud_group")
public class CrudGroupController {

    private final GetCurrentUserService getCurrentUserService;
    private final UuidConverter uuidConverter;
    private final AuthorizeUserAccessInProjectService authorizeUserAccessInProjectService;
    private final CreateCrudGroupUseCase createCrudGroupUseCase;
    private final GroupPersistence groupPersistence;
    private final GetStructureByIdQuery getStructureByIdQuery;
    private final SetStructureForCrudGroupUseCase setStructureForCrudGroupUseCase;
    private final DissolveCrudGroupUseCase dissolveCrudGroupUseCase;

    public CrudGroupController(GetCurrentUserService getCurrentUserService,
        UuidConverter uuidConverter,
        AuthorizeUserAccessInProjectService authorizeUserAccessInProjectService,
        CreateCrudGroupUseCase createCrudGroupUseCase,
        GroupPersistence groupPersistence, GetStructureByIdQuery getStructureByIdQuery,
        SetStructureForCrudGroupUseCase setStructureForCrudGroupUseCase,
        DissolveCrudGroupUseCase dissolveCrudGroupUseCase) {
        this.getCurrentUserService = getCurrentUserService;
        this.uuidConverter = uuidConverter;
        this.authorizeUserAccessInProjectService = authorizeUserAccessInProjectService;
        this.createCrudGroupUseCase = createCrudGroupUseCase;
        this.groupPersistence = groupPersistence;
        this.getStructureByIdQuery = getStructureByIdQuery;
        this.setStructureForCrudGroupUseCase = setStructureForCrudGroupUseCase;
        this.dissolveCrudGroupUseCase = dissolveCrudGroupUseCase;
    }

    @PostMapping("")
    public ResponseEntity<CreateCrudGroupResponse> createCrudGroup(
        @PathVariable("project_id") String projectIdString,
        @RequestBody CreateCrudGroupRequest request) {
        var user = getCurrentUserService.getUser();
        var projectId = new ProjectId(uuidConverter.fromString(projectIdString));
        if (!authorizeUserAccessInProjectService.authorizeUserAccessInProject(user.getId(),
            projectId)) {
            return ResponseEntity.status(403).build();
        }
        var strutureId = new StructureId(uuidConverter.fromString(request.sourceStructure));
        var groupId = createCrudGroupUseCase.createCrudGroup(strutureId, projectId);
        return ResponseEntity.ok(
            new CreateCrudGroupResponse(uuidConverter.toString(groupId.id())));
    }

    @GetMapping("/{group_id}")
    public ResponseEntity<CrudDetail> getCrudGroup(
        @PathVariable("project_id") String projectIdString,
        @PathVariable("group_id") String groupIdString) {
        var user = getCurrentUserService.getUser();
        var projectId = new ProjectId(uuidConverter.fromString(projectIdString));
        if (!authorizeUserAccessInProjectService.authorizeUserAccessInProject(user.getId(),
            projectId)) {
            return ResponseEntity.status(403).build();
        }
        var groupId = new GroupId(uuidConverter.fromString(groupIdString));
        var group = groupPersistence.findCrudById(groupId);
        var structure = group.getSource().map(getStructureByIdQuery::getStructureById);
        return ResponseEntity.ok(
            new CrudDetail(uuidConverter.toString(group.getId().id()), structure.map(
                s -> new CrudDetail.SourceStructure(uuidConverter.toString(s.getId().id()),
                    s.getName())).getOrNull()));
    }

    @PatchMapping("/{group_id}")
    public ResponseEntity<Void> setStructureForCrudGroup(
        @PathVariable("project_id") String projectIdString,
        @PathVariable("group_id") String groupIdString,
        @RequestBody SetStructureForCrudGroupRequest request) {
        var user = getCurrentUserService.getUser();
        var projectId = new ProjectId(uuidConverter.fromString(projectIdString));
        if (!authorizeUserAccessInProjectService.authorizeUserAccessInProject(user.getId(),
            projectId)) {
            return ResponseEntity.status(403).build();
        }
        var groupId = new GroupId(uuidConverter.fromString(groupIdString));
        var structureId = new StructureId(uuidConverter.fromString(request.sourceStructure));
        setStructureForCrudGroupUseCase.setStructureForCrudGroup(groupId, structureId, projectId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{group_id}")
    public ResponseEntity<Void> dissolveCrudGroup(
        @PathVariable("project_id") String projectIdString,
        @PathVariable("group_id") String groupIdString) {
        var user = getCurrentUserService.getUser();
        var projectId = new ProjectId(uuidConverter.fromString(projectIdString));
        if (!authorizeUserAccessInProjectService.authorizeUserAccessInProject(user.getId(),
            projectId)) {
            return ResponseEntity.status(403).build();
        }
        var groupId = new GroupId(uuidConverter.fromString(groupIdString));
        dissolveCrudGroupUseCase.dissolveCrudGroup(projectId, groupId);
        return ResponseEntity.ok().build();
    }


    public record SetStructureForCrudGroupRequest(String sourceStructure) {

    }

    public record CrudDetail(String id, SourceStructure sourceStructure) {

        public record SourceStructure(String id, String name) {

        }
    }

    public record CreateCrudGroupRequest(String sourceStructure) {

    }

    public record CreateCrudGroupResponse(String id) {

    }
}
