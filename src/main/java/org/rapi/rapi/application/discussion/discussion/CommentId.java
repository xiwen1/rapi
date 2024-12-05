package org.rapi.rapi.application.discussion.discussion;

import java.util.UUID;

public record CommentId(UUID id) {
    public static CommentId create() {
        return new CommentId(UUID.randomUUID());
    }
}
