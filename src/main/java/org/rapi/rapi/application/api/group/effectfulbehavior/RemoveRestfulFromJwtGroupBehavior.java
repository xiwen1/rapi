package org.rapi.rapi.application.api.group.effectfulbehavior;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.group.GroupId;
import org.rapi.rapi.application.api.group.JwtGroup;

public class RemoveRestfulFromJwtGroupBehavior {

    private final GroupPersistence groupPersistence;

    public RemoveRestfulFromJwtGroupBehavior(GroupPersistence groupPersistence) {
        this.groupPersistence = groupPersistence;
    }

    public Tuple2<JwtGroup, EndpointId> removeRestfulFromJwtGroup(GroupId groupId,
        EndpointId endpointId) {
        var group = groupPersistence.findJwtById(groupId);
        var newEndpointId = group.remove(endpointId);
        groupPersistence.saveJwt(group);
        return Tuple.of(group, newEndpointId);
    }
}
