package org.rapi.rapi.presentation.controller;

import org.rapi.rapi.application.AuthorizeUserAccessInProjectService;
import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.endpoint.GrpcEndpoint;
import org.rapi.rapi.application.api.infrastructure.mapping.UuidConverter;
import org.rapi.rapi.application.api.service.command.UpdateGrpcEndpointCommand;
import org.rapi.rapi.application.api.service.query.GetGrpcEndpointByIdQuery;
import org.rapi.rapi.application.project.project.ProjectId;
import org.rapi.rapi.presentation.GetCurrentUserService;
import org.rapi.rapi.presentation.converter.PresentationSchemaConverter;
import org.rapi.rapi.presentation.dto.SchemaDto;
import org.rapi.rapi.usecase.CreateGrpcEndpointUseCase;
import org.rapi.rapi.usecase.DeleteGrpcEndpointUseCase;
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
@RequestMapping("/api/project/{project_id}/grpc_endpoint")
public class GrpcEndpointController {

    private final UuidConverter uuidConverter;
    private final GetCurrentUserService getCurrentUserService;
    private final AuthorizeUserAccessInProjectService authorizeUserAccessInProjectService;
    private final CreateGrpcEndpointUseCase createGrpcEndpointUseCase;
    private final GetGrpcEndpointByIdQuery getGrpcEndpointByIdQuery;
    private final PresentationSchemaConverter presentationSchemaConverter;
    private final UpdateGrpcEndpointCommand updateGrpcEndpointCommand;
    private final DeleteGrpcEndpointUseCase deleteGrpcEndpointUseCase;

    public GrpcEndpointController(UuidConverter uuidConverter,
        GetCurrentUserService getCurrentUserService,
        AuthorizeUserAccessInProjectService authorizeUserAccessInProjectService,
        CreateGrpcEndpointUseCase createGrpcEndpointUseCase,
        GetGrpcEndpointByIdQuery getGrpcEndpointByIdQuery,
        PresentationSchemaConverter presentationSchemaConverter,
        UpdateGrpcEndpointCommand updateGrpcEndpointCommand,
        DeleteGrpcEndpointUseCase deleteGrpcEndpointUseCase) {
        this.uuidConverter = uuidConverter;
        this.getCurrentUserService = getCurrentUserService;
        this.authorizeUserAccessInProjectService = authorizeUserAccessInProjectService;
        this.createGrpcEndpointUseCase = createGrpcEndpointUseCase;
        this.getGrpcEndpointByIdQuery = getGrpcEndpointByIdQuery;
        this.presentationSchemaConverter = presentationSchemaConverter;
        this.updateGrpcEndpointCommand = updateGrpcEndpointCommand;
        this.deleteGrpcEndpointUseCase = deleteGrpcEndpointUseCase;
    }

    @PostMapping
    public ResponseEntity<CreateGrpcEndpointResponse> createGrpcEndpoint(
        @PathVariable("project_id") String projectIdString,
        @RequestBody CreateGrpcEndpointRequest request) {
        var projectId = new ProjectId(uuidConverter.fromString(projectIdString));
        var user = getCurrentUserService.getUser();
        if (!authorizeUserAccessInProjectService.authorizeUserAccessInProject(user.getId(),
            projectId)) {
            return ResponseEntity.status(403).build();
        }
        var endpointId = createGrpcEndpointUseCase.createGrpcEndpoint(request.name(),
            request.description(),
            request.service(), request.paraStream(), request.resultStream(), projectId);
        return ResponseEntity.ok(
            new CreateGrpcEndpointResponse(uuidConverter.toString(endpointId.id())));
    }

    @GetMapping("/{endpoint_id}")
    public ResponseEntity<GrpcEndpointDetail> getGrpcEndpointDetail(
        @PathVariable("project_id") String projectIdString,
        @PathVariable("endpoint_id") String endpointIdString) {
        var projectId = new ProjectId(uuidConverter.fromString(projectIdString));
        var user = getCurrentUserService.getUser();
        if (!authorizeUserAccessInProjectService.authorizeUserAccessInProject(user.getId(),
            projectId)) {
            return ResponseEntity.status(403).build();
        }
        var endpointId = new EndpointId(uuidConverter.fromString(endpointIdString));
        var endpoint = getGrpcEndpointByIdQuery.getGrpcEndpointById(endpointId);
        return ResponseEntity.ok(toGrpcEndpointDetail(endpoint));
    }

    @PutMapping("/{endpoint_id}")
    public ResponseEntity<UpdateGrpcEndpointResponse> updateGrpcEndpoint(
        @PathVariable("project_id") String projectIdString,
        @PathVariable("endpoint_id") String endpointIdString,
        @RequestBody GrpcEndpointDetail request) {
        var projectId = new ProjectId(uuidConverter.fromString(projectIdString));
        var user = getCurrentUserService.getUser();
        if (!authorizeUserAccessInProjectService.authorizeUserAccessInProject(user.getId(),
            projectId)) {
            return ResponseEntity.status(403).build();
        }
        var endpointId = new EndpointId(uuidConverter.fromString(endpointIdString));

        updateGrpcEndpointCommand.updateGrpcEndpoint(fromGrpcEndpointDetail(request));

        return ResponseEntity.ok(
            new UpdateGrpcEndpointResponse(uuidConverter.toString(endpointId.id())));
    }

    @DeleteMapping("/{endpoint_id}")
    public ResponseEntity<Void> deleteGrpcEndpoint(
        @PathVariable("project_id") String projectIdString,
        @PathVariable("endpoint_id") String endpointIdString) {
        var projectId = new ProjectId(uuidConverter.fromString(projectIdString));
        var user = getCurrentUserService.getUser();
        if (!authorizeUserAccessInProjectService.authorizeUserAccessInProject(user.getId(),
            projectId)) {
            return ResponseEntity.status(403).build();
        }
        var endpointId = new EndpointId(uuidConverter.fromString(endpointIdString));
        deleteGrpcEndpointUseCase.deleteGrpcEndpoint(endpointId, projectId);
        return ResponseEntity.ok().build();
    }

    public GrpcEndpointDetail toGrpcEndpointDetail(GrpcEndpoint endpoint) {
        return new GrpcEndpointDetail(uuidConverter.toString(endpoint.getId().id()),
            endpoint.getTitle(),
            endpoint.getDescription(), endpoint.getService(), endpoint.isParamStream(),
            endpoint.isResultStream(),
            presentationSchemaConverter.toSchemaDto(endpoint.getParam()),
            presentationSchemaConverter.toSchemaDto(endpoint.getResult()));
    }

    public GrpcEndpoint fromGrpcEndpointDetail(GrpcEndpointDetail grpcEndpointDetail) {
        return GrpcEndpoint.fromRaw(
            new EndpointId(uuidConverter.fromString(grpcEndpointDetail.id())),
            grpcEndpointDetail.name(), grpcEndpointDetail.description(),
            grpcEndpointDetail.service(),
            grpcEndpointDetail.paramStream(), grpcEndpointDetail.resultStream(),
            presentationSchemaConverter.fromSchemaDto(grpcEndpointDetail.paramSchema()),
            presentationSchemaConverter.fromSchemaDto(grpcEndpointDetail.resultSchema()));
    }

    public record UpdateGrpcEndpointResponse(String id) {

    }

    public record GrpcEndpointDetail(String id, String name, String description, String service,
                                     boolean paramStream, boolean resultStream,
                                     SchemaDto paramSchema, SchemaDto resultSchema) {


    }

    public record CreateGrpcEndpointRequest(String name, String description, String service,
                                            boolean paraStream, boolean resultStream) {

    }

    public record CreateGrpcEndpointResponse(String id) {

    }

}
