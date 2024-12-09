package org.rapi.rapi.application.discussion.service.command;

import org.rapi.rapi.application.discussion.author.AuthorId;
import org.rapi.rapi.application.discussion.discussion.ConversationId;
import org.rapi.rapi.application.discussion.discussion.DiscussionId;
import org.rapi.rapi.application.discussion.service.DiscussionPersistence;

public class ReopenConversationService {
    private final DiscussionPersistence discussionPersistence;

    public ReopenConversationService(DiscussionPersistence discussionPersistence) {
        this.discussionPersistence = discussionPersistence;
    }

    public void reopenConversation(DiscussionId discussionId, ConversationId conversationId, AuthorId authorId) {
        // prepare
        var discussion = discussionPersistence.findById(discussionId);

        // operate
        discussion.reopenConversation(conversationId, authorId);

        // persist
        discussionPersistence.save(discussion);
    }
}
