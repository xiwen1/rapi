package org.rapi.rapi.application.discussion.author;

import lombok.Getter;
import org.rapi.rapi.sharedkernel.Entity;

@Getter
public class Author implements Entity<AuthorId> {
    private AuthorId id;
    @Override
    public AuthorId getId() {
        return id;
    }
}
