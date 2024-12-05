package org.rapi.rapi.application.discussion.discussion;

import org.rapi.rapi.sharedkernel.Entity;

public class Conversation implements Entity<ConversationId> {
    private ConversationId id;

    @Override
    public ConversationId getId() {
        return id;
    }
}
