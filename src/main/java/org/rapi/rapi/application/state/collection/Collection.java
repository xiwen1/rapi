package org.rapi.rapi.application.state.collection;

import io.vavr.collection.List;
import lombok.Getter;
import org.rapi.rapi.application.state.state.State;
import org.rapi.rapi.application.state.state.StateId;
import org.rapi.rapi.application.state.subject.SubjectId;
import org.rapi.rapi.sharedkernel.Entity;

@Getter
public class Collection implements Entity<CollectionId> {

    public static final List<State> DEFAULT_STATES = List.of(
        State.create("New"),
        State.create("In Progress"),
        State.create("Done")
    );
    private final CollectionId id;
    private List<SubjectId> subjectIds;
    private List<StateId> stateIds;
    private StateId defaultState;

    private Collection(CollectionId id, List<SubjectId> subjectIds, List<StateId> stateIds,
        StateId defaultState) {
        this.id = id;
        this.subjectIds = subjectIds;
        this.stateIds = stateIds;
        this.defaultState = defaultState;
    }

    public static Collection create(CollectionId id, List<SubjectId> subjectIds,
        List<StateId> stateIds, StateId defaultState) {
        return new Collection(id, subjectIds, stateIds, defaultState);
    }

    public static Collection create() {
        var defaultStateIds = DEFAULT_STATES.map(State::getId);
        return new Collection(CollectionId.create(), List.empty(), defaultStateIds,
            defaultStateIds.get(0));
    }

    public void addSubject(SubjectId subjectId) {
        if (this.subjectIds.contains(subjectId)) {
            throw new IllegalArgumentException("Subject already exists");
        }
        this.subjectIds = this.subjectIds.append(subjectId);
    }

    public void addState(StateId stateId) {
        if (this.stateIds.contains(stateId)) {
            throw new IllegalArgumentException("State already exists");
        }
        this.stateIds = this.stateIds.append(stateId);
    }

    public void removeSubject(SubjectId subjectId) {
        if (!this.subjectIds.contains(subjectId)) {
            throw new IllegalArgumentException("Subject does not exist");
        }
        this.subjectIds = this.subjectIds.remove(subjectId);
    }

    public void removeState(StateId stateId) {
        if (!this.stateIds.contains(stateId)) {
            throw new IllegalArgumentException("State does not exist");
        }
        if (this.defaultState.equals(stateId)) {
            throw new IllegalArgumentException("Cannot remove default state");
        }
        this.stateIds = this.stateIds.remove(stateId);
    }

    public void changeDefaultState(StateId id) {
        if (!this.stateIds.contains(id)) {
            throw new IllegalArgumentException("State does not exist");
        }
        if (this.defaultState.equals(id)) {
            throw new IllegalArgumentException("State is already default");
        }
        this.defaultState = id;
    }

}
