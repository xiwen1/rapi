package org.rapi.rapi.application.state.subject;

import lombok.Getter;
import org.rapi.rapi.sharedkernel.Entity;

@Getter
public class Subject implements Entity<SubjectId> {
    private SubjectId id;
}
