package org.rapi.rapi.application.discussion.discussion;

import java.util.UUID;

public record DiscussionId(UUID id) {

    public static DiscussionId create() {
        return new DiscussionId(UUID.randomUUID());
    }
}
