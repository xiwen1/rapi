package org.rapi.rapi.application.state.state;

import io.vavr.collection.List;
import lombok.Getter;
import lombok.Setter;
import org.rapi.rapi.sharedkernel.Entity;

@Getter
public class State implements Entity<StateId> {
    private StateId id;
    @Setter
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
}
