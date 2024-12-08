package org.rapi.rapi.application.discussion.service;

import org.rapi.rapi.application.discussion.author.AuthorId;
import org.rapi.rapi.application.discussion.discussion.DiscussionId;

public class StartConversationService {
    private final DiscussionPersistence discussionPersistence;

    public StartConversationService(DiscussionPersistence discussionPersistence) {
        this.discussionPersistence = discussionPersistence;
    }

    public void startConversation(DiscussionId discussionId, String title, AuthorId starter) {
        // prepare
        var discussion = discussionPersistence.findById(discussionId);

        // operate
        discussion.startConversation(title, starter);

        // save
        discussionPersistence.save(discussion);
    }
}
