package org.rapi.rapi.application.state.collection;

import java.util.UUID;

public record SubjectId(UUID id) {

    public static SubjectId create() {
        return new SubjectId(UUID.randomUUID());
    }
}
