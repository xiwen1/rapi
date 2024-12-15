package org.rapi.rapi.usecase;

import io.vavr.Tuple6;
import io.vavr.collection.List;
import org.rapi.rapi.application.DomainIdMappingService;
import org.rapi.rapi.application.api.service.query.GetInventoryByIdQuery;
import org.rapi.rapi.application.auth.user.UserId;
import org.rapi.rapi.application.project.project.ProjectId;
import org.rapi.rapi.application.project.project.participant.Participant;
import org.rapi.rapi.application.project.service.query.GetProjectsByCrewIdQuery;
import org.springframework.stereotype.Service;

@Service
public class GetProjectListUseCase {

    private final GetProjectsByCrewIdQuery getProjectsByCrewIdQuery;
    private final DomainIdMappingService domainIdMappingService;
    private final GetInventoryByIdQuery getInventoryByIdQuery;

    public GetProjectListUseCase(GetProjectsByCrewIdQuery getProjectsByCrewIdQuery,
        DomainIdMappingService domainIdMappingService,
        GetInventoryByIdQuery getInventoryByIdQuery) {
        this.getProjectsByCrewIdQuery = getProjectsByCrewIdQuery;
        this.domainIdMappingService = domainIdMappingService;
        this.getInventoryByIdQuery = getInventoryByIdQuery;
    }

    // return tuple contains project id, project title, role, endpoint count, structure count, group count
    public List<Tuple6<ProjectId, String, Participant, Integer, Integer, Integer>> getProjectList(
        UserId userId) {
        var crewId = domainIdMappingService.getCrewId(userId);

        var projectList = getProjectsByCrewIdQuery.getProjectsByUserId(crewId);

        return projectList.map(project -> {
            var inventory = getInventoryByIdQuery.getInventoryById(
                domainIdMappingService.getInventoryId(project.getId()));
            var role = project.getParticipants()
                .find(participant -> participant.getCrew().equals(crewId)).get();
            var endpointCount =
                inventory.getRestfulEndpoints().size() + inventory.getGrpcEndpoints().size();
            var groupCount = inventory.getCrudGroups().size() + inventory.getJwtGroups().size();
            var structureCount = inventory.getStructures().size();
            return new Tuple6<>(project.getId(), project.getTitle(), role, endpointCount,
                structureCount, groupCount);
        });

    }
}
