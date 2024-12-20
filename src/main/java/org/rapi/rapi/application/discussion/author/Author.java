package org.rapi.rapi.application.discussion.author;

import lombok.Getter;
import org.rapi.rapi.sharedkernel.Entity;

@Getter
public class Author implements Entity<AuthorId> {

    private final AuthorId id;

    private Author(AuthorId id) {
        this.id = id;
    }

    public static Author create() {
        return new Author(AuthorId.create());
    }

    public static Author fromRaw(AuthorId id) {
        return new Author(id);
    }
}
