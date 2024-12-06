package org.rapi.rapi.application.state.subject;

import lombok.Getter;
import org.rapi.rapi.sharedkernel.Entity;

@Getter
public class Subject implements Entity<SubjectId> {

    private final SubjectId id;

    private Subject(SubjectId id) {
        this.id = id;
    }

    public static Subject create(SubjectId id) {
        return new Subject(id);
    }

    public static Subject create() {
        return new Subject(SubjectId.create());
    }
}
