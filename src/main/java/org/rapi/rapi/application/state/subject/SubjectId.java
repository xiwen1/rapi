package org.rapi.rapi.application.state.subject;

import java.util.UUID;

public record SubjectId(UUID id) {
    public static SubjectId create() {
        return new SubjectId(UUID.randomUUID());
    }
}
