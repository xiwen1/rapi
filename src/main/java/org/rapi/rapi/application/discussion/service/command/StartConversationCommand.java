package org.rapi.rapi.application.discussion.service.command;

import org.rapi.rapi.application.discussion.author.AuthorId;
import org.rapi.rapi.application.discussion.discussion.ConversationId;
import org.rapi.rapi.application.discussion.discussion.DiscussionId;
import org.rapi.rapi.application.discussion.service.DiscussionPersistence;
import org.springframework.stereotype.Service;

@Service
public class StartConversationCommand {

    private final DiscussionPersistence discussionPersistence;

    public StartConversationCommand(DiscussionPersistence discussionPersistence) {
        this.discussionPersistence = discussionPersistence;
    }

    public ConversationId startConversation(DiscussionId discussionId, String title,
        AuthorId starter) {
        // prepare
        var discussion = discussionPersistence.findById(discussionId);

        // operate
        var conversationId = discussion.startConversation(title, starter);

        // save
        discussionPersistence.save(discussion);
        return conversationId;
    }
}
