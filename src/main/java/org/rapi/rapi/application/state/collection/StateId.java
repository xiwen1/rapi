package org.rapi.rapi.application.state.collection;

import java.util.UUID;

public record StateId(UUID id) {

    public static StateId create() {
        return new StateId(UUID.randomUUID());
    }
}
