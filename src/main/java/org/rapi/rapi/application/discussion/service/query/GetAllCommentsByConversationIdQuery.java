package org.rapi.rapi.application.discussion.service.query;

import io.vavr.collection.List;
import org.rapi.rapi.application.discussion.discussion.Comment;
import org.rapi.rapi.application.discussion.discussion.ConversationId;
import org.rapi.rapi.application.discussion.discussion.DiscussionId;
import org.rapi.rapi.application.discussion.infrastructure.DiscussionPersistenceImpl;
import org.springframework.stereotype.Service;

@Service
public class GetAllCommentsByConversationIdQuery {

    private final DiscussionPersistenceImpl discussionPersistence;

    public GetAllCommentsByConversationIdQuery(DiscussionPersistenceImpl discussionPersistence) {
        this.discussionPersistence = discussionPersistence;
    }

    public List<Comment> getAllCommentsByConversationId(ConversationId conversationId,
        DiscussionId discussionId) {
        var discussion = discussionPersistence.findById(discussionId);
        return discussion.getConversations().find(c -> c.getId().equals(conversationId)).get()
            .getComments();
    }
}
