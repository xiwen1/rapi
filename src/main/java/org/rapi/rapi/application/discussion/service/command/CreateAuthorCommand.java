package org.rapi.rapi.application.discussion.service.command;

import org.rapi.rapi.application.discussion.author.Author;
import org.rapi.rapi.application.discussion.service.AuthorPersistence;
import org.springframework.stereotype.Service;

@Service
public class CreateAuthorCommand {

    private final AuthorPersistence authorPersistence;

    public CreateAuthorCommand(AuthorPersistence authorPersistence) {
        this.authorPersistence = authorPersistence;
    }

    public Author createAuthor() {
        var author = Author.create();

        authorPersistence.save(author);

        return author;
    }
}
