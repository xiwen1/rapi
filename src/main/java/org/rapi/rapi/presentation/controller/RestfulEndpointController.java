package org.rapi.rapi.presentation.controller;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import org.rapi.rapi.application.AuthorizeUserAccessInProjectService;
import org.rapi.rapi.application.DomainIdMappingService;
import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.infrastructure.mapping.UuidConverter;
import org.rapi.rapi.application.api.service.query.GetRestfulEndpointByIdQuery;
import org.rapi.rapi.application.api.service.query.GetRestfulEndpointListQuery;
import org.rapi.rapi.application.project.project.ProjectId;
import org.rapi.rapi.application.state.collection.StateId;
import org.rapi.rapi.application.state.service.query.GetCollectionByIdQuery;
import org.rapi.rapi.presentation.GetCurrentUserService;
import org.rapi.rapi.presentation.converter.RestfulEndpointDetailConverter;
import org.rapi.rapi.presentation.dto.RestfulEndpointDetailDto;
import org.rapi.rapi.usecase.CreateRestfulEndpointUseCase;
import org.rapi.rapi.usecase.DeleteRestfulEndpointUseCase;
import org.rapi.rapi.usecase.UpdateRestfulEndpointUseCase;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/project/{project_id}/restful_endpoint")
public class RestfulEndpointController {

    private final AuthorizeUserAccessInProjectService authorizeUserAccessInProjectService;
    private final GetRestfulEndpointListQuery getRestfulEndpointListQuery;
    private final DomainIdMappingService domainIdMappingService;
    private final CreateRestfulEndpointUseCase createRestfulEndpointUseCase;
    private final GetCurrentUserService getCurrentUserService;
    private final GetRestfulEndpointByIdQuery getRestfulEndpointByIdQuery;
    private final RestfulEndpointDetailConverter restfulEndpointDetailConverter;
    private final GetCollectionByIdQuery getCollectionByIdQuery;
    private final UpdateRestfulEndpointUseCase updateRestfulEndpointUseCase;
    private final UuidConverter uuidConverter;
    private final DeleteRestfulEndpointUseCase deleteRestfulEndpointUseCase;

    public RestfulEndpointController(
        AuthorizeUserAccessInProjectService authorizeUserAccessInProjectService,
        GetRestfulEndpointListQuery getRestfulEndpointListQuery,
        DomainIdMappingService domainIdMappingService,
        CreateRestfulEndpointUseCase createRestfulEndpointUseCase,
        GetCurrentUserService getCurrentUserService,
        GetRestfulEndpointByIdQuery getRestfulEndpointByIdQuery,
        RestfulEndpointDetailConverter restfulEndpointDetailConverter,
        GetCollectionByIdQuery getCollectionByIdQuery,
        UpdateRestfulEndpointUseCase updateRestfulEndpointUseCase, UuidConverter uuidConverter,
        DeleteRestfulEndpointUseCase deleteRestfulEndpointUseCase) {
        this.authorizeUserAccessInProjectService = authorizeUserAccessInProjectService;
        this.getRestfulEndpointListQuery = getRestfulEndpointListQuery;
        this.domainIdMappingService = domainIdMappingService;
        this.createRestfulEndpointUseCase = createRestfulEndpointUseCase;
        this.getCurrentUserService = getCurrentUserService;
        this.getRestfulEndpointByIdQuery = getRestfulEndpointByIdQuery;
        this.restfulEndpointDetailConverter = restfulEndpointDetailConverter;
        this.getCollectionByIdQuery = getCollectionByIdQuery;
        this.updateRestfulEndpointUseCase = updateRestfulEndpointUseCase;
        this.uuidConverter = uuidConverter;
        this.deleteRestfulEndpointUseCase = deleteRestfulEndpointUseCase;
    }

    @GetMapping("")
    public ResponseEntity<List<GetRestfulEndpointListResponseItem>> getRestfulEndpointList(
        @PathVariable("project_id") String projectIdString) {
        var user = getCurrentUserService.getUser();
        var projectId = new ProjectId(UUID.fromString(projectIdString));
        if (!authorizeUserAccessInProjectService.authorizeUserAccessInProject(user.getId(),
            projectId)) {
            return ResponseEntity.status(403).build();
        }

        var inventoryId = domainIdMappingService.getInventoryId(projectId);
        var endpoints = getRestfulEndpointListQuery.getRestfulEndpointList(inventoryId);
        return ResponseEntity.ok(endpoints.map(
                e -> new GetRestfulEndpointListResponseItem(e.getId().id().toString(), e.getTitle()))
            .toJavaList());
    }

    @PostMapping("")
    public ResponseEntity<CreateRestfulEndpointResponse> createRestfulEndpoint(
        @PathVariable("project_id") String projectIdString,
        @RequestBody CreateRestfulEndpointRequest request) {
        var user = getCurrentUserService.getUser();
        var projectId = new ProjectId(UUID.fromString(projectIdString));
        var endpointId = createRestfulEndpointUseCase.createRestfulEndpoint(
            request.name(), request.description(), HttpMethod.valueOf(request.httpMethod()),
            projectId,
            user.getId());

        return ResponseEntity.ok(new CreateRestfulEndpointResponse(endpointId.id().toString()));
    }

    @GetMapping("/{endpoint_id}")
    public ResponseEntity<RestfulEndpointDetailDto> getRestfulEndpointDetail(
        @PathVariable("project_id") String projectIdString,
        @PathVariable("endpoint_id") String endpointIdString) {
        var user = getCurrentUserService.getUser();
        var projectId = new ProjectId(UUID.fromString(projectIdString));
        if (!authorizeUserAccessInProjectService.authorizeUserAccessInProject(user.getId(),
            projectId)) {
            Logger.getLogger("RestfulEndpointController")
                .info("User is not a member of the project");
            return ResponseEntity.status(403).build();
        }

        var endpointId = new EndpointId(UUID.fromString(endpointIdString));
        var collectionId = domainIdMappingService.getCollectionId(projectId);
        var subjectId = domainIdMappingService.getSubjectId(endpointId);
        var collection = getCollectionByIdQuery.getCollectionById(collectionId);
        var subjectOption = collection.getSubjects().find(s -> s.getId().equals(subjectId));
        if (subjectOption.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var endpoint = getRestfulEndpointByIdQuery.getRestfulEndpointById(endpointId);
        return ResponseEntity.ok(restfulEndpointDetailConverter.toRestfulEndpointDetail(endpoint,
            subjectOption.get().getCurrentState()));

    }

    @PutMapping("/{endpoint_id}")
    public ResponseEntity<UpdateRestfulEndpointResponse> updateRestfulEndpoint(
        @PathVariable("project_id") String projectIdString,
        @PathVariable("endpoint_id") String endpointIdString,
        @RequestBody RestfulEndpointDetailDto request) {
        var user = getCurrentUserService.getUser();
        var projectId = new ProjectId(UUID.fromString(projectIdString));
        var endpointId = new EndpointId(UUID.fromString(endpointIdString));
        if (!authorizeUserAccessInProjectService.authorizeUserAccessInProject(user.getId(),
            projectId)) {
            return ResponseEntity.status(403).build();
        }
        var stateId = new StateId(uuidConverter.fromString(request.getState()));
        updateRestfulEndpointUseCase.updateRestfulEndpoint(
            restfulEndpointDetailConverter.fromRestfulEndpointDto(request), stateId, projectId);
        return ResponseEntity.ok(new UpdateRestfulEndpointResponse(endpointId.id().toString()));
    }

    @DeleteMapping("/{endpoint_id}")
    public ResponseEntity<Void> deleteRestfulEndpoint(
        @PathVariable("project_id") String projectIdString,
        @PathVariable("endpoint_id") String endpointIdString) {
        var user = getCurrentUserService.getUser();
        var projectId = new ProjectId(UUID.fromString(projectIdString));
        if (!authorizeUserAccessInProjectService.authorizeUserAccessInProject(user.getId(),
            projectId)) {
            return ResponseEntity.status(403).build();
        }
        var endpointId = new EndpointId(UUID.fromString(endpointIdString));
        deleteRestfulEndpointUseCase.deleteRestfulEndpoint(endpointId, projectId);
        return ResponseEntity.ok().build();
    }


    public record GetRestfulEndpointListResponseItem(String id, String name) {

    }

    public record CreateRestfulEndpointRequest(String name, String description, String httpMethod) {

    }

    public record CreateRestfulEndpointResponse(String id) {

    }

    public record UpdateRestfulEndpointResponse(String id) {

    }


}
