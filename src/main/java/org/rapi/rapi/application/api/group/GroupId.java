package org.rapi.rapi.application.api.group;

import java.util.UUID;

public record GroupId(UUID id) {
    public GroupId {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
    }

    public static GroupId create() {
        return new GroupId(UUID.randomUUID());
    }
}
