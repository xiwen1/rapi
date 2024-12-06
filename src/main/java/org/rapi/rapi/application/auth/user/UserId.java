package org.rapi.rapi.application.auth.user;

import java.util.UUID;

public record UserId(UUID id) {

    public static UserId create() {
        return new UserId(UUID.randomUUID());
    }
}
