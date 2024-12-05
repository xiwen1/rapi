package org.rapi.rapi.application.state.subject;

import org.rapi.rapi.sharedkernel.Entity;

public class Subject implements Entity<SubjectId> {
    private SubjectId id;

    @Override
    public SubjectId getId() {
        return id;
    }
}
