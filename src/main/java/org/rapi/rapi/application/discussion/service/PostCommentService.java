package org.rapi.rapi.application.discussion.service;

import org.rapi.rapi.application.discussion.author.AuthorId;
import org.rapi.rapi.application.discussion.discussion.ConversationId;
import org.rapi.rapi.application.discussion.discussion.DiscussionId;

public class PostCommentService {
    private final DiscussionPersistence discussionPersistence;

    public PostCommentService(DiscussionPersistence discussionPersistence) {
        this.discussionPersistence = discussionPersistence;
    }

    public void postComment(DiscussionId discussionId, ConversationId conversationId, AuthorId authorId, String content) {
        // prepare
        var discussion = discussionPersistence.findById(discussionId);

        // operate
        discussion.postComment(conversationId, content, authorId);

        // persist
        discussionPersistence.save(discussion);
    }
}
