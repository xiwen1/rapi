package org.rapi.rapi.application.api.endpoint;

import java.util.UUID;

public record EndpointId(UUID id) {
    public static EndpointId create() {
        return new EndpointId(UUID.randomUUID());
    }
}
