package org.rapi.rapi.usecase;

import org.rapi.rapi.application.DomainIdMappingService;
import org.rapi.rapi.application.auth.service.query.GetUserByIdQuery;
import org.rapi.rapi.application.auth.user.UserId;
import org.rapi.rapi.application.project.service.CrewPersistence;
import org.rapi.rapi.presentation.dto.UserProfileDto;
import org.springframework.stereotype.Service;

@Service
public class GetUserProfileUseCase {

    private final GetUserByIdQuery getUserByIdQuery;
    private final CrewPersistence crewPersistence;
    private final DomainIdMappingService domainIdMappingService;

    public GetUserProfileUseCase(GetUserByIdQuery getUserByIdQuery,
        CrewPersistence crewPersistence, DomainIdMappingService domainIdMappingService) {
        this.getUserByIdQuery = getUserByIdQuery;
        this.crewPersistence = crewPersistence;
        this.domainIdMappingService = domainIdMappingService;
    }

    public UserProfileDto getUserProfile(UserId userId) {
        var user = getUserByIdQuery.getUserById(userId);
        var crewId = domainIdMappingService.getCrewId(userId);
        var crew = crewPersistence.findById(crewId);
        return new UserProfileDto(user.getId().id().toString(), user.getUsername(),
            crew.getEmail());
    }
}
