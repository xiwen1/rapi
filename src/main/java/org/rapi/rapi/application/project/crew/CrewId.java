package org.rapi.rapi.application.project.crew;

import java.util.UUID;

public record CrewId(UUID id) {

    public static CrewId create() {
        return new CrewId(UUID.randomUUID());
    }
}
