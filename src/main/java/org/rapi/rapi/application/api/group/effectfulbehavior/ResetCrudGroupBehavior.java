package org.rapi.rapi.application.api.group.effectfulbehavior;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.rapi.rapi.application.api.group.CrudGroup;
import org.rapi.rapi.application.api.group.GroupId;

public class ResetCrudGroupBehavior {

    private final GroupPersistence groupPersistence;

    public ResetCrudGroupBehavior(GroupPersistence groupPersistence) {
        this.groupPersistence = groupPersistence;
    }

    public Tuple2<CrudGroup, CrudGroup.CrudEndpointIds> resetCrudGroup(GroupId id) {
        var group = groupPersistence.findCrudById(id);
        var endpointIds = group.reset();
        groupPersistence.saveCrud(group);
        return Tuple.of(group, endpointIds);
    }
}
