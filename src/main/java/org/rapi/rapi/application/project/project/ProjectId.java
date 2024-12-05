package org.rapi.rapi.application.project.project;

import java.util.UUID;

public record ProjectId(UUID id) {
    public static ProjectId create() {
        return new ProjectId(UUID.randomUUID());
    }
}
