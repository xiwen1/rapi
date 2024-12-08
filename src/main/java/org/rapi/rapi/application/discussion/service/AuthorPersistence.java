package org.rapi.rapi.application.discussion.service;

import org.rapi.rapi.application.discussion.author.Author;
import org.rapi.rapi.application.discussion.author.AuthorId;

public interface AuthorPersistence {

    void save(Author author);

    Author findById(AuthorId authorId);

    void delete(AuthorId authorId);

}
