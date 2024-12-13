package org.rapi.rapi.application.discussion.service.command;

import org.rapi.rapi.application.discussion.author.AuthorId;
import org.rapi.rapi.application.discussion.discussion.CommentId;
import org.rapi.rapi.application.discussion.discussion.ConversationId;
import org.rapi.rapi.application.discussion.discussion.DiscussionId;
import org.rapi.rapi.application.discussion.service.DiscussionPersistence;
import org.springframework.stereotype.Service;

@Service
public class PostCommentCommand {

    private final DiscussionPersistence discussionPersistence;

    public PostCommentCommand(DiscussionPersistence discussionPersistence) {
        this.discussionPersistence = discussionPersistence;
    }

    public CommentId postComment(DiscussionId discussionId, ConversationId conversationId,
        AuthorId authorId, String content) {
        // prepare
        var discussion = discussionPersistence.findById(discussionId);

        // operate
        var commentId = discussion.postComment(conversationId, content, authorId);

        // persist
        discussionPersistence.save(discussion);
        return commentId;
    }
}
