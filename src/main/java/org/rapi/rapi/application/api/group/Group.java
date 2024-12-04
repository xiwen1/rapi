package org.rapi.rapi.application.api.group;

import lombok.Getter;
import org.rapi.rapi.sharedkernel.Entity;

@Getter
public abstract class Group implements Entity<GroupId> {

    private final GroupId id;

    protected Group(GroupId id) {
        this.id = id;
    }
}
