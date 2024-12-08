package org.rapi.rapi.application.discussion.service;

import org.rapi.rapi.application.discussion.discussion.Discussion;

public class CreateDiscussionService {
    private final DiscussionPersistence discussionPersistence;

    public CreateDiscussionService(DiscussionPersistence discussionPersistence) {
        this.discussionPersistence = discussionPersistence;
    }

    public Discussion createDiscussion() {
        var discussion = Discussion.create();

        discussionPersistence.save(discussion);

        return discussion;
    }
}
