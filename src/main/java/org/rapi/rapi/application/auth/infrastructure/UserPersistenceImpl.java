package org.rapi.rapi.application.auth.infrastructure;

import org.rapi.rapi.application.auth.infrastructure.mapping.UserMappingService;
import org.rapi.rapi.application.auth.infrastructure.repository.UserRepository;
import org.rapi.rapi.application.auth.service.UserPersistence;
import org.rapi.rapi.application.auth.user.User;
import org.rapi.rapi.application.auth.user.UserId;
import org.springframework.stereotype.Service;

@Service
public class UserPersistenceImpl implements UserPersistence {

    private final UserRepository userRepository;

    private final UserMappingService userMappingService;

    public UserPersistenceImpl(UserRepository userRepository,
        UserMappingService userMappingService) {
        this.userRepository = userRepository;
        this.userMappingService = userMappingService;
    }

    @Override
    public void save(User user) {
        userRepository.save(userMappingService.toUserDto(user));
    }

    @Override
    public User findById(UserId userId) {
        return userMappingService.fromUserDto(
            userRepository.findById(userId.id().toString()).orElseThrow());
    }

    @Override
    public User findByUsername(String username) {
        return userMappingService.fromUserDto(
            userRepository.findByUsername(username).orElseThrow());
    }

    @Override
    public void delete(UserId userId) {
        userRepository.deleteById(userId.id().toString());
    }
}
