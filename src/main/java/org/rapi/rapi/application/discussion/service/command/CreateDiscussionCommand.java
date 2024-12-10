package org.rapi.rapi.application.discussion.service.command;

import org.rapi.rapi.application.discussion.discussion.Discussion;
import org.rapi.rapi.application.discussion.service.DiscussionPersistence;
import org.springframework.stereotype.Service;

@Service
public class CreateDiscussionCommand {

    private final DiscussionPersistence discussionPersistence;

    public CreateDiscussionCommand(DiscussionPersistence discussionPersistence) {
        this.discussionPersistence = discussionPersistence;
    }

    public Discussion createDiscussion() {
        var discussion = Discussion.create();

        discussionPersistence.save(discussion);

        return discussion;
    }
}
