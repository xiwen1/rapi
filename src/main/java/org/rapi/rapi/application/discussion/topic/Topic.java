package org.rapi.rapi.application.discussion.topic;

import io.vavr.control.Option;
import lombok.Getter;
import org.rapi.rapi.application.discussion.discussion.DiscussionId;
import org.rapi.rapi.sharedkernel.Entity;

@Getter
public class Topic implements Entity<TopicId> {

    private final TopicId id;
    private Option<DiscussionId> discussionId;

    private Topic(TopicId id, Option<DiscussionId> discussionId) {
        this.id = id;
        this.discussionId = discussionId;
    }

    public static Topic create(TopicId id, Option<DiscussionId> discussionId) {
        return new Topic(id, discussionId);
    }

    public static Topic create(Option<DiscussionId> discussionId) {
        return new Topic(TopicId.create(), discussionId);
    }

    public void assignDiscussion(DiscussionId discussionId) {
        this.discussionId = Option.some(discussionId);
    }

    public void unassignDiscussion() {
        this.discussionId = Option.none();
    }
}
