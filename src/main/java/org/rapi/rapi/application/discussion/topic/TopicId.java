package org.rapi.rapi.application.discussion.topic;

import java.util.UUID;

public record TopicId(UUID id) {
    public static TopicId create() {
        return new TopicId(UUID.randomUUID());
    }
}
