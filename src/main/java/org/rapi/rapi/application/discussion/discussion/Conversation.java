package org.rapi.rapi.application.discussion.discussion;

import io.vavr.collection.List;
import lombok.Getter;
import lombok.Setter;
import org.rapi.rapi.sharedkernel.Entity;

@Getter
public class Conversation implements Entity<ConversationId> {
    private ConversationId id;
    @Setter
    private String title;
    private List<CommentId> commentIds;
    @Setter
    private boolean isClosed;

    private Conversation(ConversationId id, String title, List<CommentId> commentIds, boolean isClosed) {
        this.id = id;
        this.title = title;
        this.commentIds = commentIds;
        this.isClosed = isClosed;
    }

    public static Conversation create(ConversationId id, String title, List<CommentId> commentIds, boolean isClosed) {
        return new Conversation(id, title, commentIds, isClosed);
    }

    public static Conversation create(String title) {
        return new Conversation(ConversationId.create(), title, List.empty(), false);
    }

    public void addComment(CommentId commentId) {
        if (this.isClosed) {
            throw new IllegalStateException("Conversation is closed");
        }
        if (this.commentIds.contains(commentId)) {
            throw new IllegalArgumentException("Comment already exists");
        }
        this.commentIds = this.commentIds.append(commentId);
    }

    public void closeConversation() {
        this.isClosed = true;
    }

}
