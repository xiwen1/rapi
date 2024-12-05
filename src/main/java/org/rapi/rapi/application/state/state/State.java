package org.rapi.rapi.application.state.state;

import lombok.Getter;
import org.rapi.rapi.application.state.collection.Collection;
import org.rapi.rapi.sharedkernel.Entity;

@Getter
public class State implements Entity<StateId> {
    private StateId id;
    private String name;

    @Override
    public StateId getId() {
        return id;
    }

    private State(StateId id, String name) {
        this.id = id;
        this.name = name;
    }

    public static State create(StateId id, String name) {
        return new State(id, name);
    }

    public static State create(String name) {
        return new State(StateId.create(), name);
    }

    public void setName(String name) {
        if (Collection.DEFAULT_STATES.contains(this)) {
            throw new IllegalArgumentException("System default states cannot be updated");
        }
        this.name = name;
    }
}
