package org.rapi.rapi.application.discussion.discussion;

import io.vavr.collection.List;
import lombok.Getter;
import org.rapi.rapi.application.discussion.author.AuthorId;
import org.rapi.rapi.sharedkernel.Entity;

@Getter
public class Conversation implements Entity<ConversationId> {

    private final ConversationId id;
    private final String title;
    private List<Comment> comments;
    private boolean isClosed;
    private final AuthorId starter;

    private Conversation(ConversationId id, String title, List<Comment> comments,
        boolean isClosed, AuthorId starter) {
        this.id = id;
        this.title = title;
        this.comments = comments;
        this.isClosed = isClosed;
        this.starter = starter;
    }

    public static Conversation fromRaw(ConversationId id, String title, List<Comment> commentIds,
        boolean isClosed, AuthorId starter) {
        return new Conversation(id, title, commentIds, isClosed, starter);
    }

    public static Conversation create(String title, AuthorId starter) {
        return new Conversation(ConversationId.create(), title, List.empty(), false, starter);
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
        this.comments = this.comments.append(Comment.create("Conversation closed.", this.starter));
    }

    public void reopen(AuthorId author) {
        if (!this.isClosed) {
            throw new IllegalStateException("Conversation is already open");
        }
        this.isClosed = false;
        this.comments = this.comments.append(Comment.create("Conversation reopened.", author));
    }

}
