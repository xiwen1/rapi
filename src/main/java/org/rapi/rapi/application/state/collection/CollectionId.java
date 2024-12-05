package org.rapi.rapi.application.state.collection;

import java.util.UUID;

public record CollectionId(UUID id) {
    public static CollectionId create() {
        return new CollectionId(UUID.randomUUID());
    }
}
