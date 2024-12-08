package org.rapi.rapi.application.discussion.service;

import org.rapi.rapi.application.discussion.author.AuthorId;
import org.rapi.rapi.application.discussion.discussion.ConversationId;
import org.rapi.rapi.application.discussion.discussion.DiscussionId;

public class CloseConversationService {
    private final DiscussionPersistence discussionPersistence;

    public CloseConversationService(DiscussionPersistence discussionPersistence) {
        this.discussionPersistence = discussionPersistence;
    }

    public void closeConversation(DiscussionId discussionId, ConversationId conversationId, AuthorId authorId)
        throws IllegalAccessException {
        // prepare
        var discussion = discussionPersistence.findById(discussionId);

        // operate
        discussion.closeConversation(conversationId, authorId);

        // persist
        discussionPersistence.save(discussion);
    }
}
