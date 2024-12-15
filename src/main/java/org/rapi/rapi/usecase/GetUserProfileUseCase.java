package org.rapi.rapi.usecase;

import org.rapi.rapi.application.DomainIdMappingService;
import org.rapi.rapi.application.auth.service.query.GetUserByIdQuery;
import org.rapi.rapi.application.auth.user.UserId;
import org.rapi.rapi.application.project.service.CrewPersistence;
import org.rapi.rapi.application.project.service.query.GetCrewByIdQuery;
import org.rapi.rapi.presentation.dto.UserProfileDto;
import org.springframework.stereotype.Service;

@Service
public class GetUserProfileUseCase {

    private final GetUserByIdQuery getUserByIdQuery;
    private final CrewPersistence crewPersistence;
    private final DomainIdMappingService domainIdMappingService;
    private final GetCrewByIdQuery getCrewByIdQuery;

    public GetUserProfileUseCase(GetUserByIdQuery getUserByIdQuery,
        CrewPersistence crewPersistence, DomainIdMappingService domainIdMappingService,
        GetCrewByIdQuery getCrewByIdQuery) {
        this.getUserByIdQuery = getUserByIdQuery;
        this.crewPersistence = crewPersistence;
        this.domainIdMappingService = domainIdMappingService;
        this.getCrewByIdQuery = getCrewByIdQuery;
    }

    public UserProfileDto getUserProfile(UserId userId) {
        var user = getUserByIdQuery.getUserById(userId);
        var crewId = domainIdMappingService.getCrewId(userId);
        var crew = getCrewByIdQuery.getCrewById(crewId);
        return new UserProfileDto(user.getId().id().toString(), user.getUsername(),
            crew.getEmail());
    }
}
