package org.rapi.rapi.application.api.group.effectfulbehavior;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.rapi.rapi.application.api.endpoint.effectfulbehavior.EndpointPersistence;
import org.rapi.rapi.application.api.group.CrudGroup;
import org.rapi.rapi.application.api.group.GroupId;

public class ResetCrudGroupBehavior {

    private final GroupPersistence groupPersistence;
    private final EndpointPersistence endpointPersistence;

    public ResetCrudGroupBehavior(GroupPersistence groupPersistence,
        EndpointPersistence endpointPersistence) {
        this.groupPersistence = groupPersistence;
        this.endpointPersistence = endpointPersistence;
    }

    public Tuple2<CrudGroup, CrudGroup.CrudEndpointIds> resetCrudGroup(GroupId id) {
        var group = (CrudGroup) groupPersistence.findById(id);
        var endpointIds = group.reset();
        endpointIds.listEndpointIds().forEach(endpointPersistence::deleteRestful);
        groupPersistence.saveCrud(group);
        return Tuple.of(group, endpointIds);
    }
}
