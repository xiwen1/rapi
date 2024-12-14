package org.rapi.rapi.presentation.controller;

import java.util.List;
import org.rapi.rapi.application.AuthorizeUserAccessInProjectService;
import org.rapi.rapi.application.DomainIdMappingService;
import org.rapi.rapi.application.api.infrastructure.mapping.UuidConverter;
import org.rapi.rapi.application.project.project.ProjectId;
import org.rapi.rapi.application.state.collection.StateId;
import org.rapi.rapi.application.state.service.command.CreateStateCommand;
import org.rapi.rapi.application.state.service.command.DeleteStateCommand;
import org.rapi.rapi.application.state.service.command.SetDefaultStateCommand;
import org.rapi.rapi.application.state.service.query.GetCollectionByIdQuery;
import org.rapi.rapi.presentation.GetCurrentUserService;
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
@RequestMapping("/api/project/{project_id}/state")
public class StateController {

    private final UuidConverter uuidConverter;
    private final DomainIdMappingService domainIdMappingService;
    private final GetCurrentUserService getCurrentUserService;
    private final AuthorizeUserAccessInProjectService authorizeUserAccessInProjectService;
    private final GetCollectionByIdQuery getCollectionByIdQuery;
    private final CreateStateCommand createStateCommand;
    private final SetDefaultStateCommand setDefaultStateCommand;
    private final DeleteStateCommand deleteStateCommand;

    public StateController(UuidConverter uuidConverter,
        DomainIdMappingService domainIdMappingService, GetCurrentUserService getCurrentUserService,
        AuthorizeUserAccessInProjectService authorizeUserAccessInProjectService,
        GetCollectionByIdQuery getCollectionByIdQuery, CreateStateCommand createStateCommand,
        SetDefaultStateCommand setDefaultStateCommand, DeleteStateCommand deleteStateCommand) {
        this.uuidConverter = uuidConverter;
        this.domainIdMappingService = domainIdMappingService;
        this.getCurrentUserService = getCurrentUserService;
        this.authorizeUserAccessInProjectService = authorizeUserAccessInProjectService;
        this.getCollectionByIdQuery = getCollectionByIdQuery;
        this.createStateCommand = createStateCommand;
        this.setDefaultStateCommand = setDefaultStateCommand;
        this.deleteStateCommand = deleteStateCommand;
    }

    @GetMapping("")
    public ResponseEntity<List<GetAllStateResponseItem>> getAllStates(
        @PathVariable("project_id") String projectIdString) {
        var projectId = new ProjectId(uuidConverter.fromString(projectIdString));
        var collectionId = domainIdMappingService.getCollectionId(projectId);
        var user = getCurrentUserService.getUser();
        if (!authorizeUserAccessInProjectService.authorizeUserAccessInProject(user.getId(),
            projectId)) {
            return ResponseEntity.status(403).build();
        }
        var collection = getCollectionByIdQuery.getCollectionById(collectionId);
        return ResponseEntity.ok(collection.getStates().map(s -> new GetAllStateResponseItem(
            uuidConverter.toString(s.getId().id()), s.getName())).toJavaList());
    }


    @PostMapping("")
    public ResponseEntity<CreateStateResponse> createState(
        @PathVariable("project_id") String projectIdString,
        @RequestBody CreateStateRequest request) {
        var projectId = new ProjectId(uuidConverter.fromString(projectIdString));
        var user = getCurrentUserService.getUser();
        if (!authorizeUserAccessInProjectService.authorizeUserAccessInProject(user.getId(),
            projectId)) {
            return ResponseEntity.status(403).build();
        }

        var collectionId = domainIdMappingService.getCollectionId(projectId);
        var state = createStateCommand.createState(collectionId, request.name);
        return ResponseEntity.ok(
            new CreateStateResponse(uuidConverter.toString(state.getId().id())));
    }

    @DeleteMapping("/{state_id}")
    public ResponseEntity<Void> deleteState(@PathVariable("project_id") String projectIdString,
        @PathVariable("state_id") String stateIdString) {
        var projectId = new ProjectId(uuidConverter.fromString(projectIdString));
        var user = getCurrentUserService.getUser();
        if (!authorizeUserAccessInProjectService.authorizeUserAccessInProject(user.getId(),
            projectId)) {
            return ResponseEntity.status(403).build();
        }
        var collectionId = domainIdMappingService.getCollectionId(projectId);
        deleteStateCommand.deleteState(collectionId,
            new StateId(uuidConverter.fromString(stateIdString)));
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/default")
    public ResponseEntity<Void> changeDefaultState(
        @PathVariable("project_id") String projectIdString,
        @RequestBody ChangeDefaultStateRequest request) {
        var projectId = new ProjectId(uuidConverter.fromString(projectIdString));
        var user = getCurrentUserService.getUser();
        if (!authorizeUserAccessInProjectService.authorizeUserAccessInProject(user.getId(),
            projectId)) {
            return ResponseEntity.status(403).build();
        }
        var collectionId = domainIdMappingService.getCollectionId(projectId);
        setDefaultStateCommand.setDefaultState(collectionId,
            new StateId(uuidConverter.fromString(request.id)));
        return ResponseEntity.ok().build();
    }


    public record ChangeDefaultStateRequest(String id) {

    }


    public record CreateStateRequest(String name) {

    }

    public record CreateStateResponse(String id) {

    }

    public record GetAllStateResponseItem(String id, String name) {

    }

}
