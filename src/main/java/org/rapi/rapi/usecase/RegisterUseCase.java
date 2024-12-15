package org.rapi.rapi.usecase;

import org.rapi.rapi.application.DomainIdMappingService;
import org.rapi.rapi.application.auth.service.command.RegisterUserCommand;
import org.rapi.rapi.application.discussion.service.command.CreateAuthorCommand;
import org.rapi.rapi.application.project.service.command.CreateCrewCommand;
import org.springframework.stereotype.Service;

@Service
public class RegisterUseCase {

    private final RegisterUserCommand registerUserCommand;
    private final CreateCrewCommand createCrewCommand;
    private final CreateAuthorCommand createAuthorCommand;
    private final DomainIdMappingService domainIdMappingService;

    public RegisterUseCase(RegisterUserCommand registerUserCommand,
        CreateCrewCommand createCrewCommand,
        CreateAuthorCommand createAuthorCommand, DomainIdMappingService domainIdMappingService) {
        this.registerUserCommand = registerUserCommand;
        this.createCrewCommand = createCrewCommand;
        this.createAuthorCommand = createAuthorCommand;
        this.domainIdMappingService = domainIdMappingService;
    }

    public void register(String username, String password, String email) {
        var user = registerUserCommand.createUser(username, password);
        var crew = createCrewCommand.createCrew(email);
        var author = createAuthorCommand.createAuthor();
        domainIdMappingService.saveMapping(user.getId(), crew.getId());
        domainIdMappingService.saveMapping(user.getId(), author.getId());
    }

}
