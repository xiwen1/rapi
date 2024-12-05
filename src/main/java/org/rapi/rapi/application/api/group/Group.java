package org.rapi.rapi.application.api.group;

import io.vavr.collection.List;
import lombok.Getter;
import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.sharedkernel.Entity;

@Getter
public abstract class Group implements Entity<GroupId> {

    private final GroupId id;

    protected Group(GroupId id) {
        this.id = id;
    }

    public abstract List<EndpointId> getGeneratedEndpoints();
}
