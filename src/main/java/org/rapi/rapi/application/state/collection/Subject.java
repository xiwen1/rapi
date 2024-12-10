package org.rapi.rapi.application.state.collection;

import lombok.Getter;
import org.rapi.rapi.sharedkernel.Entity;

@Getter
public class Subject implements Entity<SubjectId> {

    private final SubjectId id;

    private StateId currentState;

    private Subject(SubjectId id, StateId currentState) {
        this.id = id;
        this.currentState = currentState;
    }

    public static Subject fromRaw(SubjectId id, StateId currentState) {
        return new Subject(id, currentState);
    }

    public static Subject create(StateId currentState) {
        return new Subject(SubjectId.create(), currentState);
    }

    public void reassignState(StateId newState) {
        if (newState.equals(this.currentState)) {
            throw new IllegalArgumentException("Cannot reassign the same state");
        }
        this.currentState = newState;
    }
}
