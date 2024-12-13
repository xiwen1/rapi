package org.rapi.rapi.application.discussion.service.command;

import org.rapi.rapi.application.discussion.author.AuthorId;
import org.rapi.rapi.application.discussion.discussion.ConversationId;
import org.rapi.rapi.application.discussion.discussion.DiscussionId;
import org.rapi.rapi.application.discussion.service.DiscussionPersistence;
import org.springframework.stereotype.Service;

@Service
public class CloseConversationCommand {

    private final DiscussionPersistence discussionPersistence;

    public CloseConversationCommand(DiscussionPersistence discussionPersistence) {
        this.discussionPersistence = discussionPersistence;
    }

    public void closeConversation(DiscussionId discussionId, ConversationId conversationId,
        AuthorId authorId) {
        // prepare
        var discussion = discussionPersistence.findById(discussionId);

        // operate
        try {
            discussion.closeConversation(conversationId, authorId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to close conversation", e);
        }
        // persist
        discussionPersistence.save(discussion);
    }
}
