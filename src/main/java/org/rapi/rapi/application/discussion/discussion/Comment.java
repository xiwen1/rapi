package org.rapi.rapi.application.discussion.discussion;

import lombok.Getter;
import org.rapi.rapi.application.discussion.author.AuthorId;
import org.rapi.rapi.sharedkernel.Entity;

@Getter
public class Comment implements Entity<CommentId> {

    private final CommentId id;
    private final String content;
    private final AuthorId authorId;

    private Comment(CommentId id, String content, AuthorId authorId) {
        this.id = id;
        this.content = content;
        this.authorId = authorId;
    }

    public static Comment fromRaw(CommentId id, String content, AuthorId authorId) {
        return new Comment(id, content, authorId);
    }

    public static Comment create(String content, AuthorId authorId) {
        return new Comment(CommentId.create(), content, authorId);
    }
}
