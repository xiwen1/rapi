package org.rapi.rapi.presentation.controller;

import java.util.List;
import org.rapi.rapi.application.AuthorizeUserAccessInProjectService;
import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.group.GroupId;
import org.rapi.rapi.application.api.infrastructure.mapping.UuidConverter;
import org.rapi.rapi.application.api.service.query.GetJwtGroupSourceEndpointsQuery;
import org.rapi.rapi.application.project.project.ProjectId;
import org.rapi.rapi.presentation.GetCurrentUserService;
import org.rapi.rapi.usecase.CreateJwtGroupUseCase;
import org.rapi.rapi.usecase.DissolveJwtGroupUseCase;
import org.rapi.rapi.usecase.SetEndpointsForJwtGroupUseCase;
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
@RequestMapping("/api/project/{project_id}/jwt_group")
public class JwtGroupController {

    private final GetCurrentUserService getCurrentUserService;
    private final UuidConverter uuidConverter;
    private final AuthorizeUserAccessInProjectService authorizeUserAccessInProjectService;
    private final CreateJwtGroupUseCase createJwtGroupUseCase;
    private final GetJwtGroupSourceEndpointsQuery getJwtGroupSourceEndpointsQuery;
    private final SetEndpointsForJwtGroupUseCase setEndpointsForJwtGroupUseCase;
    private final DissolveJwtGroupUseCase dissolveJwtGroupUseCase;

    public JwtGroupController(GetCurrentUserService getCurrentUserService,
        UuidConverter uuidConverter,
        AuthorizeUserAccessInProjectService authorizeUserAccessInProjectService,
        CreateJwtGroupUseCase createJwtGroupUseCase,
        GetJwtGroupSourceEndpointsQuery getJwtGroupSourceEndpointsQuery,
        SetEndpointsForJwtGroupUseCase setEndpointsForJwtGroupUseCase,
        DissolveJwtGroupUseCase dissolveJwtGroupUseCase) {
        this.getCurrentUserService = getCurrentUserService;
        this.uuidConverter = uuidConverter;
        this.authorizeUserAccessInProjectService = authorizeUserAccessInProjectService;
        this.createJwtGroupUseCase = createJwtGroupUseCase;
        this.getJwtGroupSourceEndpointsQuery = getJwtGroupSourceEndpointsQuery;
        this.setEndpointsForJwtGroupUseCase = setEndpointsForJwtGroupUseCase;
        this.dissolveJwtGroupUseCase = dissolveJwtGroupUseCase;
    }

    @PostMapping("")
    public ResponseEntity<CreateJwtGroupResponse> createJwtGroup(
        @PathVariable("project_id") String projectIdString,
        @RequestBody CreateJwtGroupRequest request) {
        var user = getCurrentUserService.getUser();
        var projectId = new ProjectId(uuidConverter.fromString(projectIdString));
        if (!authorizeUserAccessInProjectService.authorizeUserAccessInProject(user.getId(),
            projectId)) {
            return ResponseEntity.status(403).build();
        }
        var groupId = createJwtGroupUseCase.createJwtGroup(user.getId(), projectId);
        return ResponseEntity.ok(new CreateJwtGroupResponse(uuidConverter.toString(groupId.id())));
    }

    @GetMapping("/{group_id}")
    public ResponseEntity<JwtGroupDetail> getJwtGroupDetail(
        @PathVariable("project_id") String projectIdString,
        @PathVariable("group_id") String groupIdString) {
        var user = getCurrentUserService.getUser();
        var projectId = new ProjectId(uuidConverter.fromString(projectIdString));
        if (!authorizeUserAccessInProjectService.authorizeUserAccessInProject(user.getId(),
            projectId)) {
            return ResponseEntity.status(403).build();
        }
        var groupId = new GroupId(uuidConverter.fromString(groupIdString));
        var sourceEndpoints = getJwtGroupSourceEndpointsQuery.getJwtGroupSourceEndpoints(groupId);
        return ResponseEntity.ok(new JwtGroupDetail(groupIdString, sourceEndpoints.map(
            e -> new JwtGroupDetail.SourceEndpoint(uuidConverter.toString(e.getId().id()),
                e.getTitle())).toJavaList()));
    }

    @PatchMapping("/{group_id}")
    public ResponseEntity<Void> setSourceEndpoints(
        @PathVariable("project_id") String projectIdString,
        @PathVariable("group_id") String groupIdString,
        @RequestBody List<String> sourceEndpoints) {
        var user = getCurrentUserService.getUser();
        var projectId = new ProjectId(uuidConverter.fromString(projectIdString));
        if (!authorizeUserAccessInProjectService.authorizeUserAccessInProject(user.getId(),
            projectId)) {
            return ResponseEntity.status(403).build();
        }
        var groupId = new GroupId(uuidConverter.fromString(groupIdString));
        setEndpointsForJwtGroupUseCase.setEndpointsForJwtGroup(
            io.vavr.collection.List.ofAll(sourceEndpoints)
                .map(s -> new EndpointId(uuidConverter.fromString(s))),
            groupId, projectId
        );
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{group_id}")
    public ResponseEntity<Void> dissolveJwtGroup(
        @PathVariable("project_id") String projectIdString,
        @PathVariable("group_id") String groupIdString) {
        var user = getCurrentUserService.getUser();
        var projectId = new ProjectId(uuidConverter.fromString(projectIdString));
        if (!authorizeUserAccessInProjectService.authorizeUserAccessInProject(user.getId(),
            projectId)) {
            return ResponseEntity.status(403).build();
        }
        var groupId = new GroupId(uuidConverter.fromString(groupIdString));
        dissolveJwtGroupUseCase.dissolveJwtGroup(projectId, groupId);
        return ResponseEntity.ok().build();
    }


    public record JwtGroupDetail(String id, List<SourceEndpoint> sourceEndpoints) {

        public record SourceEndpoint(String id, String name) {

        }
    }


    public record CreateJwtGroupRequest() {

    }

    public record CreateJwtGroupResponse(String id) {

    }
}
