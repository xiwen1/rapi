package org.rapi.rapi.application.discussion.discussion;

import io.vavr.collection.List;
import lombok.Getter;
import org.rapi.rapi.application.discussion.author.AuthorId;
import org.rapi.rapi.sharedkernel.Entity;

@Getter
public class Discussion implements Entity<DiscussionId> {

    private final DiscussionId id;
    private List<Conversation> conversations;

    private Discussion(DiscussionId id, List<Conversation> conversations) {
        this.id = id;
        this.conversations = conversations;
    }

    public static Discussion create(DiscussionId id, List<Conversation> conversations) {
        return new Discussion(id, conversations);
    }

    public static Discussion create(List<Conversation> conversations) {
        return new Discussion(DiscussionId.create(), conversations);
    }

    public static Discussion create() {
        return new Discussion(DiscussionId.create(), List.empty());
    }

    public void addConversation(String conversationTitle) {
        var conversation = Conversation.create(conversationTitle);
        this.conversations = this.conversations.append(conversation);
    }

    public void makeComment(ConversationId conversationId, String content, AuthorId authorId) {
        var conversation = this.conversations.find(c -> c.getId().equals(conversationId))
                .getOrElseThrow(() -> new IllegalArgumentException("Conversation not found"));
        conversation.postComment(Comment.create(content, authorId));
    }

    public void closeConversation(ConversationId conversationId) {
        var conversation = this.conversations.find(c -> c.getId().equals(conversationId))
                .getOrElseThrow(() -> new IllegalArgumentException("Conversation not found"));
        conversation.close();
    }

    public void reopenConversation(ConversationId conversationId) {
        var conversation = this.conversations.find(c -> c.getId().equals(conversationId))
                .getOrElseThrow(() -> new IllegalArgumentException("Conversation not found"));
        conversation.reopen();
    }
}
