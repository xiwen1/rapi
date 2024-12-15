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

    public static Discussion fromRaw(DiscussionId id, List<Conversation> conversations) {
        return new Discussion(id, conversations);
    }

    public static Discussion create(List<Conversation> conversations) {
        return new Discussion(DiscussionId.create(), conversations);
    }

    public static Discussion create() {
        return new Discussion(DiscussionId.create(), List.empty());
    }

    public ConversationId startConversation(String conversationTitle, AuthorId starter) {
        var conversation = Conversation.create(conversationTitle, starter);
        this.conversations = this.conversations.append(conversation);
        return conversation.getId();
    }

    public CommentId postComment(ConversationId conversationId, String content, AuthorId authorId) {
        var conversation = this.conversations.find(c -> c.getId().equals(conversationId))
            .getOrElseThrow(() -> new IllegalArgumentException("Conversation not found"));
        var comment = Comment.create(content, authorId);
        conversation.postComment(comment);
        return comment.getId();
    }

    public void closeConversation(ConversationId conversationId, AuthorId authorId)
        throws IllegalAccessException {
        var conversation = this.conversations.find(c -> c.getId().equals(conversationId))
            .getOrElseThrow(() -> new IllegalArgumentException("Conversation not found"));
        if (!conversation.getStarter().equals(authorId)) {
            throw new IllegalAccessException("Conversation can only be closed by starter");
        }
        conversation.close();
    }

    public void reopenConversation(ConversationId conversationId, AuthorId authorId) {
        var conversation = this.conversations.find(c -> c.getId().equals(conversationId))
            .getOrElseThrow(() -> new IllegalArgumentException("Conversation not found"));
        conversation.reopen(authorId);
    }
}
