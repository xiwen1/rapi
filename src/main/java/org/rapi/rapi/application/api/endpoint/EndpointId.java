package org.rapi.rapi.application.api.endpoint;

import java.util.UUID;

public record EndpointId(UUID id) {
    public EndpointId {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
    }

    public static EndpointId create() {
        return new EndpointId(UUID.randomUUID());
    }
}
