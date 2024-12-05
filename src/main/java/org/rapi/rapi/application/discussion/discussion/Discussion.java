package org.rapi.rapi.application.discussion.discussion;

import io.vavr.collection.List;
import lombok.Getter;
import org.rapi.rapi.sharedkernel.Entity;

@Getter
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

    public void addConversation(ConversationId conversationId) {
        if (this.conversationIds.contains(conversationId)) {
            throw new IllegalArgumentException("Conversation already exists");
        }
        this.conversationIds = this.conversationIds.append(conversationId);
    }


}
