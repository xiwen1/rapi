package org.rapi.rapi.application.discussion.discussion;

import io.vavr.collection.List;
import lombok.Getter;
import org.rapi.rapi.sharedkernel.Entity;

@Getter
public class Conversation implements Entity<ConversationId> {

    private final ConversationId id;
    private final String title;
    private List<Comment> comments;
    private boolean isClosed;

    private Conversation(ConversationId id, String title, List<Comment> comments,
        boolean isClosed) {
        this.id = id;
        this.title = title;
        this.comments = comments;
        this.isClosed = isClosed;
    }

    public static Conversation create(ConversationId id, String title, List<Comment> commentIds,
        boolean isClosed) {
        return new Conversation(id, title, commentIds, isClosed);
    }

    public static Conversation create(String title) {
        return new Conversation(ConversationId.create(), title, List.empty(), false);
    }

    public void postComment(Comment comment) {
        if (this.isClosed) {
            throw new IllegalStateException("Conversation is closed");
        }
        if (this.comments.contains(comment)) {
            throw new IllegalArgumentException("Comment already exists");
        }
        this.comments = this.comments.append(comment);
    }

    public void close() {
        if (this.isClosed) {
            throw new IllegalStateException("Conversation is already closed");
        }
        this.isClosed = true;
    }

    public void reopen() {
        if (!this.isClosed) {
            throw new IllegalStateException("Conversation is already open");
        }
        this.isClosed = false;
    }

}
