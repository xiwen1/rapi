package org.rapi.rapi.application.discussion.author;

import java.util.UUID;

public record AuthorId(UUID id) {
    public static AuthorId create() {
        return new AuthorId(UUID.randomUUID());
    }
}
