package org.rapi.rapi.application.api.group.effectfulbehavior;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.endpoint.effectfulbehavior.EndpointPersistence;
import org.rapi.rapi.application.api.group.GroupId;
import org.rapi.rapi.application.api.group.JwtGroup;

public class RemoveRestfulFromJwtGroupBehavior {

    private final GroupPersistence groupPersistence;
    private final EndpointPersistence endpointPersistence;

    public RemoveRestfulFromJwtGroupBehavior(GroupPersistence groupPersistence,
        EndpointPersistence endpointPersistence) {
        this.groupPersistence = groupPersistence;
        this.endpointPersistence = endpointPersistence;
    }

    public Tuple2<JwtGroup, EndpointId> removeRestfulFromJwtGroup(GroupId groupId,
        EndpointId endpointId) {
        var group = (JwtGroup) groupPersistence.findById(groupId);
        var newEndpointId = group.remove(endpointId);
        groupPersistence.saveJwt(group);
        return Tuple.of(group, newEndpointId);
    }
}
