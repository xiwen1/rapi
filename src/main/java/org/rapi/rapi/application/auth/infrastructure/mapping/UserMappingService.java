package org.rapi.rapi.application.auth.infrastructure.mapping;

import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.rapi.rapi.application.auth.infrastructure.dto.UserDto;
import org.rapi.rapi.application.auth.user.User;
import org.springframework.stereotype.Service;

@Service
public class UserMappingService {

    private final ModelMapper userModelMapper;

    public UserMappingService(ModelMapper userModelMapper) {
        this.userModelMapper = userModelMapper;
    }

    public UserDto toUserDto(User user) {
        return userModelMapper.map(user, UserDto.class);
    }

    public User fromUserDto(UserDto userDto) {
        return userModelMapper.map(userDto, User.class);
    }

    public UUID fromStringId(String uuid) {
        return userModelMapper.map(uuid, UUID.class);
    }

    public String toStringId(UUID uuid) {
        return userModelMapper.map(uuid, String.class);
    }
}
