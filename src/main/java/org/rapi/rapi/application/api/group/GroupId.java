package org.rapi.rapi.application.api.group;

import java.util.UUID;

public record GroupId(UUID id) {

    public static GroupId create() {
        return new GroupId(UUID.randomUUID());
    }
}
