package org.rapi.rapi.application.discussion.discussion;

import org.rapi.rapi.sharedkernel.Entity;

public class Comment implements Entity<CommentId> {
    private CommentId id;

    @Override
    public CommentId getId() {
        return id;
    }
}
