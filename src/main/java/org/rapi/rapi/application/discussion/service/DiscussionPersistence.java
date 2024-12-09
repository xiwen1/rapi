package org.rapi.rapi.application.discussion.service;

import org.rapi.rapi.application.discussion.discussion.Discussion;
import org.rapi.rapi.application.discussion.discussion.DiscussionId;

public interface DiscussionPersistence {

    void save(Discussion discussion);

    Discussion findById(DiscussionId discussionId);

    void delete(DiscussionId discussionId);

}
