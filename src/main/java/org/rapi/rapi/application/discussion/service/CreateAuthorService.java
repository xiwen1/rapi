package org.rapi.rapi.application.discussion.service;

import org.rapi.rapi.application.discussion.author.Author;

public class CreateAuthorService {
    private final AuthorPersistence authorPersistence;

    public CreateAuthorService(AuthorPersistence authorPersistence) {
        this.authorPersistence = authorPersistence;
    }

    public Author createAuthor() {
        var author = Author.create();

        authorPersistence.save(author);

        return author;
    }
}
