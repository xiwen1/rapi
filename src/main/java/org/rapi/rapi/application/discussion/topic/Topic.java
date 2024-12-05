package org.rapi.rapi.application.discussion.topic;

import io.vavr.control.Option;
import lombok.Getter;
import org.rapi.rapi.application.discussion.discussion.ConversationId;
import org.rapi.rapi.sharedkernel.Entity;

@Getter
public class Topic implements Entity<TopicId> {
    private TopicId id;
    private Option<ConversationId> conversationId;

    private Topic(TopicId id, Option<ConversationId> conversationId) {
        this.id = id;
        this.conversationId = conversationId;
    }

    public static Topic create(TopicId id, Option<ConversationId> conversationId) {
        return new Topic(id, conversationId);
    }

    public static Topic create(Option<ConversationId> conversationId) {
        return new Topic(TopicId.create(), conversationId);
    }

    public void assignConversation(ConversationId conversationId) {
        this.conversationId = Option.some(conversationId);
    }

    public void resetConversation() {
        this.conversationId = Option.none();
    }
}
