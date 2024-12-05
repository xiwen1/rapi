package org.rapi.rapi.application.discussion.discussion;

import java.util.UUID;

public record ConversationId(UUID id) {
    public static ConversationId create() {
        return new ConversationId(UUID.randomUUID());
    }
}
