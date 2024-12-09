package org.rapi.rapi.application.api.endpoint;

import java.util.UUID;

public record EndpointId(UUID id) {

    public static EndpointId create() {
        return new EndpointId(UUID.randomUUID());
    }

    public static EndpointId fromString(String id) {
        return new EndpointId(UUID.fromString(id));
    }
}
