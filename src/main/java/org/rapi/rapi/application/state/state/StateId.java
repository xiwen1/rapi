package org.rapi.rapi.application.state.state;

import java.util.UUID;

public record StateId(UUID id) {

    public static StateId create() {
        return new StateId(UUID.randomUUID());
    }
}
