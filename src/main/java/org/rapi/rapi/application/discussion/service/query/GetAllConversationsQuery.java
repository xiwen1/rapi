package org.rapi.rapi.application.discussion.service.query;

import io.vavr.collection.List;
import org.rapi.rapi.application.discussion.discussion.Conversation;
import org.rapi.rapi.application.discussion.discussion.DiscussionId;
import org.rapi.rapi.application.discussion.service.DiscussionPersistence;
import org.springframework.stereotype.Service;

@Service
public class GetAllConversationsQuery {

    private final DiscussionPersistence discussionPersistence;

    public GetAllConversationsQuery(DiscussionPersistence discussionPersistence) {
        this.discussionPersistence = discussionPersistence;
    }

    public List<Conversation> getAllConversations(DiscussionId discussionId) {
        return discussionPersistence.findById(discussionId).getConversations();
    }
}
