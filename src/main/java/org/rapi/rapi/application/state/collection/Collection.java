package org.rapi.rapi.application.state.collection;

import io.vavr.collection.List;
import lombok.Getter;
import org.rapi.rapi.sharedkernel.Entity;

@Getter
public class Collection implements Entity<CollectionId> {

    public static final List<State> DEFAULT_STATES = List.of(State.create("New"),
        State.create("In Progress"), State.create("Done"));
    private final CollectionId id;
    private List<Subject> subjects;
    private List<State> states;
    private StateId defaultState;

    private Collection(CollectionId id, List<Subject> subjects, List<State> states,
        StateId defaultState) {
        this.id = id;
        this.subjects = subjects;
        this.states = states;
        this.defaultState = defaultState;
    }

    public static Collection create(CollectionId id, List<Subject> subjectIds, List<State> stateIds,
        StateId defaultState) {
        return new Collection(id, subjectIds, stateIds, defaultState);
    }

    public static Collection create() {
        return new Collection(CollectionId.create(), List.empty(), DEFAULT_STATES,
            DEFAULT_STATES.get(0).getId());
    }

    public void setStateForSubject(StateId stateId, SubjectId subjectId) {
        if (!getStateIds().contains(stateId)) {
            throw new IllegalArgumentException("State does not exist");
        }
        if (!getSubjectIds().contains(subjectId)) {
            throw new IllegalArgumentException("Subject does not exist");
        }
        this.subjects.find(s -> s.getId().equals(subjectId)).get().reassignState(stateId);
    }

    public Subject createSubject() {
        var subject = Subject.create(defaultState);
        this.subjects = this.subjects.append(subject);
        return subject;
    }

    public State createState(String name) {
        if (this.states.find(s -> s.getName().equals(name)).isDefined()) {
            throw new IllegalArgumentException("State with name already exists");
        }
        var state = State.create(name);
        this.states = this.states.append(state);
        return state;
    }

    public void renameState(StateId stateId, String name) {
        if (!getStateIds().contains(stateId)) {
            throw new IllegalArgumentException("State does not exist");
        }
        this.states.find(s -> s.getId().equals(stateId)).get().rename(name);
    }

    public void removeSubject(SubjectId subjectId) {
        if (!getSubjectIds().contains(subjectId)) {
            throw new IllegalArgumentException("Subject does not exist");
        }
        var subject = this.subjects.find(s -> s.getId().equals(subjectId)).get();
        this.subjects = this.subjects.remove(subject);
    }

    private List<SubjectId> getSubjectIds() {
        return this.subjects.map(Subject::getId);
    }

    public void removeState(StateId stateId) {
        if (!getStateIds().contains(stateId)) {
            throw new IllegalArgumentException("State does not exist");
        }
        if (this.defaultState.equals(stateId)) {
            throw new IllegalArgumentException("Cannot remove default state");
        }
        var state = this.states.find(s -> s.getId().equals(stateId)).get();
        this.subjects.forEach(s -> {
            if (s.getCurrentState().equals(stateId)) {
                s.reassignState(defaultState);
            }
        });
        this.states = this.states.remove(state);
    }

    public void editState(StateId stateId, String name) {
        if (!getStateIds().contains(stateId)) {
            throw new IllegalArgumentException("State does not exist");
        }
        this.states.find(s -> s.getId().equals(stateId)).get().rename(name);
    }

    private List<StateId> getStateIds() {
        return this.states.map(State::getId);
    }

    public void changeDefaultState(StateId id) {
        if (!getStateIds().contains(id)) {
            throw new IllegalArgumentException("State does not exist");
        }
        if (this.defaultState.equals(id)) {
            throw new IllegalArgumentException("State is already default");
        }
        this.subjects.forEach(s -> {
            if (s.getCurrentState().equals(this.defaultState)) {
                s.reassignState(id);
            }
        });
        this.defaultState = id;
    }

}
