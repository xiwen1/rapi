package org.rapi.rapi.application.discussion.infrastructure;

import org.rapi.rapi.application.discussion.discussion.Discussion;
import org.rapi.rapi.application.discussion.discussion.DiscussionId;
import org.rapi.rapi.application.discussion.infrastructure.mapping.DiscussionMappingService;
import org.rapi.rapi.application.discussion.infrastructure.repository.DiscussionRepository;
import org.rapi.rapi.application.discussion.service.DiscussionPersistence;
import org.springframework.stereotype.Service;

@Service
public class DiscussionPersistenceImpl implements DiscussionPersistence {

    private final DiscussionRepository discussionRepository;
    private final DiscussionMappingService discussionMappingService;

    public DiscussionPersistenceImpl(DiscussionRepository discussionRepository,
        DiscussionMappingService discussionMappingService) {
        this.discussionRepository = discussionRepository;
        this.discussionMappingService = discussionMappingService;
    }

    @Override
    public void save(Discussion discussion) {
        discussionRepository.save(discussionMappingService.toDiscussionDto(discussion));
    }

    @Override
    public Discussion findById(DiscussionId discussionId) {
        return discussionMappingService.fromDiscussionDto(
            discussionRepository.findById(discussionId.id().toString()).orElseThrow());
    }

    @Override
    public void delete(DiscussionId discussionId) {
        discussionRepository.deleteById(discussionId.id().toString());
    }
}
