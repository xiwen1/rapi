package org.rapi.rapi.presentation.controller;

import java.util.List;
import java.util.UUID;
import org.rapi.rapi.application.AuthorizeUserAccessInProjectService;
import org.rapi.rapi.application.DomainIdMappingService;
import org.rapi.rapi.application.api.service.query.GetCrudGroupListQuery;
import org.rapi.rapi.application.api.service.query.GetGrpcEndpointListQuery;
import org.rapi.rapi.application.api.service.query.GetJwtGroupListQuery;
import org.rapi.rapi.application.api.service.query.GetRestfulEndpointByIdQuery;
import org.rapi.rapi.application.api.service.query.GetRestfulEndpointListQuery;
import org.rapi.rapi.application.api.service.query.GetStructureListQuery;
import org.rapi.rapi.application.auth.service.query.GetUserByIdQuery;
import org.rapi.rapi.application.auth.user.User;
import org.rapi.rapi.application.project.project.ProjectId;
import org.rapi.rapi.application.project.project.participant.Admin;
import org.rapi.rapi.application.project.project.participant.Member;
import org.rapi.rapi.application.project.service.command.DisbandProjectCommand;
import org.rapi.rapi.application.project.service.query.GetProjectByIdQuery;
import org.rapi.rapi.application.state.service.query.GetCollectionByIdQuery;
import org.rapi.rapi.usecase.CreateProjectUseCase;
import org.rapi.rapi.usecase.GetProjectListUseCase;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    private final CreateProjectUseCase createProjectUseCase;
    private final GetProjectListUseCase getProjectListUseCase;
    private final AuthorizeUserAccessInProjectService authorizeUserAccessInProjectService;
    private final DomainIdMappingService domainIdMappingService;
    private final GetRestfulEndpointListQuery getRestfulEndpointListQuery;
    private final GetGrpcEndpointListQuery getGrpcEndpointListQuery;
    private final GetStructureListQuery getStructureListQuery;
    private final GetCrudGroupListQuery getCrudGroupListQuery;
    private final GetJwtGroupListQuery getJwtGroupListQuery;
    private final GetProjectByIdQuery getProjectByIdQuery;
    private final GetCollectionByIdQuery getCollectionByIdQuery;
    private final GetRestfulEndpointByIdQuery getRestfulEndpointByIdQuery;
    private final GetUserByIdQuery getUserByIdQuery;
    private final DisbandProjectCommand disbandProjectCommand;

    public ProjectController(CreateProjectUseCase createProjectUseCase,
        GetProjectListUseCase getProjectListUseCase,
        AuthorizeUserAccessInProjectService authorizeUserAccessInProjectService,
        DomainIdMappingService domainIdMappingService,
        GetRestfulEndpointListQuery getRestfulEndpointListQuery,
        GetGrpcEndpointListQuery getGrpcEndpointListQuery,
        GetStructureListQuery getStructureListQuery, GetCrudGroupListQuery getCrudGroupListQuery,
        GetJwtGroupListQuery getJwtGroupListQuery, GetProjectByIdQuery getProjectByIdQuery,
        GetCollectionByIdQuery getCollectionByIdQuery,
        GetRestfulEndpointByIdQuery getRestfulEndpointByIdQuery,
        GetUserByIdQuery getUserByIdQuery, DisbandProjectCommand disbandProjectCommand) {
        this.createProjectUseCase = createProjectUseCase;
        this.getProjectListUseCase = getProjectListUseCase;
        this.authorizeUserAccessInProjectService = authorizeUserAccessInProjectService;
        this.domainIdMappingService = domainIdMappingService;
        this.getRestfulEndpointListQuery = getRestfulEndpointListQuery;
        this.getGrpcEndpointListQuery = getGrpcEndpointListQuery;
        this.getStructureListQuery = getStructureListQuery;
        this.getCrudGroupListQuery = getCrudGroupListQuery;
        this.getJwtGroupListQuery = getJwtGroupListQuery;
        this.getProjectByIdQuery = getProjectByIdQuery;
        this.getCollectionByIdQuery = getCollectionByIdQuery;
        this.getRestfulEndpointByIdQuery = getRestfulEndpointByIdQuery;
        this.getUserByIdQuery = getUserByIdQuery;
        this.disbandProjectCommand = disbandProjectCommand;
    }

    private static User getUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @PostMapping("/")
    public CreateProjectResponse createProject(CreateProjectReqeust request) {
        var user = getUser();
        var projectId = createProjectUseCase.createProject(user.getId(), request.name());
        return new CreateProjectResponse(projectId.id().toString());
    }

    @GetMapping("/")
    public List<GetProjectListResponseItem> getProjectList() {
        var user = getUser();
        var projectList = getProjectListUseCase.getProjectList(user.getId());
        return projectList.map(project -> {
            var role = switch (project._3()) {
                case Admin ignored -> "Admin";
                case Member ignored -> "Member";
                default -> throw new IllegalStateException("Unexpected value: " + project._3());
            };
            return new GetProjectListResponseItem(project._1().id().toString(), project._2(), role,
                project._4(), project._5(), project._6());
        }).toJavaList();
    }

    @GetMapping("/{id}")
    public GetProjectOverviewResponse getProjectOverview(
        @PathVariable("id") String projectIdString) {
        var user = getUser();
        var projectId = new ProjectId(UUID.fromString(projectIdString));
        if (authorizeUserAccessInProjectService.authorizeUserAccessInProject(user.getId(),
            projectId)) {
            throw new IllegalArgumentException("User is not a member of the project");
        }
        var inventoryId = domainIdMappingService.getInventoryId(projectId);
        var collectionId = domainIdMappingService.getCollectionId(projectId);
        var collection = getCollectionByIdQuery.getCollectionById(collectionId);
        var restfulEndpoints = getRestfulEndpointListQuery.getRestfulEndpointList(inventoryId);
        var grpcEndpoints = getGrpcEndpointListQuery.getGrpcEndpoints(inventoryId);
        var structures = getStructureListQuery.getStructureList(inventoryId);
        var crudGroups = getCrudGroupListQuery.getCrudGroupList(inventoryId);
        var jwtGroups = getJwtGroupListQuery.getJwtGroupList(inventoryId);
        var crews = getProjectByIdQuery.getProjectById(projectId).getParticipants();
        var states = collection.getStates();

        var returnCrudGroups = crudGroups.map(g -> new GetProjectOverviewResponse.Group(
            g.getId().id().toString(), "CRUD",
            g.getGeneratedEndpoints().map(getRestfulEndpointByIdQuery::getRestfulEndpointById)
                .map(GetProjectOverviewResponse.RestfulEndpoint::toResponseDto).toJavaList()
        ));

        var returnJwtGroups = jwtGroups.map(g -> new GetProjectOverviewResponse.Group(
            g.getId().id().toString(), "JWT",
            g.getGeneratedEndpoints().map(getRestfulEndpointByIdQuery::getRestfulEndpointById)
                .map(GetProjectOverviewResponse.RestfulEndpoint::toResponseDto).toJavaList()
        ));

        var returnCrews = crews.map(c -> new GetProjectOverviewResponse.Crew(
            c.getCrew().id().toString(),
            getUserByIdQuery.getUserById(domainIdMappingService.getUserId(c.getCrew()))
                .getUsername(),
            switch (c) {
                case Admin ignored -> "Admin";
                case Member ignored -> "Member";
                default -> throw new IllegalStateException("Unexpected value: " + c);
            }
        )).toJavaList();

        var returnStates = states.map(s -> new GetProjectOverviewResponse.State(
            s.getId().id().toString(), s.getName(), collection.getDefaultState().equals(s.getId())
        )).toJavaList();

        return new GetProjectOverviewResponse(
            restfulEndpoints.map(
                e -> new GetProjectOverviewResponse.RestfulEndpoint(e.getId().toString(),
                    e.getTitle(), e.getMethod().toString())).toJavaList(),
            grpcEndpoints.map(e -> new GetProjectOverviewResponse.GrpcEndpoint(
                e.getId().toString(), e.getTitle(), e.getService())).toJavaList(),
            structures.map(
                    s -> new GetProjectOverviewResponse.Structure(s.getId().toString(), s.getName()))
                .toJavaList(),
            returnCrudGroups.appendAll(returnJwtGroups).toJavaList(),
            returnCrews, returnStates
        );

    }

    @DeleteMapping("/{id}")
    public void DisbandProject(@PathVariable("id") String projectIdString) {
        var user = getUser();
        var projectId = new ProjectId(UUID.fromString(projectIdString));
        if (authorizeUserAccessInProjectService.authorizeOwnerInProject(user.getId(),
            projectId)) {
            throw new IllegalArgumentException("User is not a owner of the project");
        }
        disbandProjectCommand.disbandProject(projectId);
    }

    public record CreateProjectReqeust(String name) {

    }

    public record CreateProjectResponse(String id) {

    }

    public record GetProjectListResponseItem(String id, String title, String role,
                                             int endpointCount,
                                             int StructureCount, int GroupCount) {

    }

    public record GetProjectOverviewResponse(List<RestfulEndpoint> restfulEndpoints,
                                             List<GrpcEndpoint> grpcEndpoints,
                                             List<Structure> structures, List<Group> groups,
                                             List<Crew> crews, List<State> states) {

        record RestfulEndpoint(String id, String name, String httpMethod) {

            public static RestfulEndpoint toResponseDto(
                org.rapi.rapi.application.api.endpoint.RestfulEndpoint endpoint) {
                return new RestfulEndpoint(endpoint.getId().id().toString(), endpoint.getTitle(),
                    endpoint.getMethod().toString());
            }
        }

        record GrpcEndpoint(String id, String name, String service) {

        }

        record Structure(String id, String name) {

        }

        record Group(String id, String type, List<RestfulEndpoint> endpoints) {

        }

        record Crew(String id, String name, String role) {

        }

        record State(String id, String name, boolean isDefault) {

        }
    }
}
