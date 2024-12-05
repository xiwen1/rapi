package org.rapi.rapi.application.discussion.discussion;

import io.vavr.control.Option;
import lombok.Getter;
import org.rapi.rapi.sharedkernel.Entity;

@Getter
public class Topic implements Entity<TopicId> {
    private TopicId id;
    private Option<ConversationId> conversationId;

    @Override
    public TopicId getId() {
        return id;
    }

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

    public ConversationId unassignConversation() {
        var ret = this.conversationId.get();
        this.conversationId = Option.none();
        return ret;
    }
}
