package org.rapi.rapi.application.api.group;

import java.util.UUID;

public record GroupId(UUID id) {

    public static GroupId create() {
        return new GroupId(UUID.randomUUID());
    }

    public static GroupId fromString(String id) {
        return new GroupId(UUID.fromString(id));
    }
}
