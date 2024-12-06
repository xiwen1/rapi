package org.rapi.rapi.application.state.collection;

import lombok.Getter;
import org.rapi.rapi.sharedkernel.Entity;

@Getter
public class State implements Entity<StateId> {

    private final StateId id;
    private String name;

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

    public void rename(String name) {
        this.name = name;
    }

}
