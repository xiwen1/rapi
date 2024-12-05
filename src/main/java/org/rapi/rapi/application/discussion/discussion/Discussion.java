package org.rapi.rapi.application.discussion.discussion;

import io.vavr.collection.List;
import org.rapi.rapi.sharedkernel.Entity;

public class Discussion implements Entity<DiscussionId> {
    private DiscussionId id;
    private List<ConversationId> conversationIds;
    @Override
    public DiscussionId getId() {
        return id;
    }

    private Discussion(DiscussionId id, List<ConversationId> conversationIds) {
        this.id = id;
        this.conversationIds = conversationIds;
    }

    public static Discussion create(DiscussionId id, List<ConversationId> conversationIds) {
        return new Discussion(id, conversationIds);
    }

    public static Discussion create(List<ConversationId> conversationIds) {
        return new Discussion(DiscussionId.create(), conversationIds);
    }

    public static Discussion create() {
        return new Discussion(DiscussionId.create(), List.empty());
    }

    public ConversationId assignConversation(ConversationId conversationId) {
        this.conversationIds = this.conversationIds.append(conversationId);
        return conversationId;
    }
}
