package org.rapi.rapi.application.discussion.infrastructure;

import org.rapi.rapi.application.discussion.author.Author;
import org.rapi.rapi.application.discussion.author.AuthorId;
import org.rapi.rapi.application.discussion.infrastructure.mapping.DiscussionMappingService;
import org.rapi.rapi.application.discussion.infrastructure.repository.AuthorRepository;
import org.rapi.rapi.application.discussion.service.AuthorPersistence;

public class AuthorPersistenceImpl implements AuthorPersistence {

    private final AuthorRepository authorRepository;

    private final DiscussionMappingService discussionMappingService;

    public AuthorPersistenceImpl(AuthorRepository authorRepository,
        DiscussionMappingService discussionMappingService) {
        this.authorRepository = authorRepository;
        this.discussionMappingService = discussionMappingService;
    }

    @Override
    public void save(Author author) {
        authorRepository.save(discussionMappingService.toAuthorDto(author));
    }

    @Override
    public Author findById(AuthorId authorId) {
        return discussionMappingService.fromAuthorDto(
            authorRepository.findById(authorId.id().toString()).orElseThrow());
    }

    @Override
    public void delete(AuthorId authorId) {
        authorRepository.deleteById(authorId.id().toString());
    }
}
