package org.rapi.rapi.application.discussion.service.command;

import org.rapi.rapi.application.discussion.discussion.DiscussionId;
import org.rapi.rapi.application.discussion.infrastructure.DiscussionPersistenceImpl;
import org.springframework.stereotype.Service;

@Service
public class DeleteDiscussionCommand {

    private final DiscussionPersistenceImpl discussionPersistence;

    public DeleteDiscussionCommand(DiscussionPersistenceImpl discussionPersistence) {
        this.discussionPersistence = discussionPersistence;
    }

    public void deleteDiscussion(DiscussionId discussionId) {
        discussionPersistence.delete(discussionId);
    }
}
