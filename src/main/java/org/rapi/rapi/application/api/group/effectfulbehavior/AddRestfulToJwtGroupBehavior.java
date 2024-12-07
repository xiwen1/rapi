package org.rapi.rapi.application.api.group.effectfulbehavior;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.endpoint.RestfulEndpoint;
import org.rapi.rapi.application.api.endpoint.effectfulbehavior.EndpointPersistence;
import org.rapi.rapi.application.api.group.GroupId;
import org.rapi.rapi.application.api.group.JwtGroup;

public class AddRestfulToJwtGroupBehavior {

    private final GroupPersistence groupPersistence;
    private final EndpointPersistence endpointPersistence;

    public AddRestfulToJwtGroupBehavior(GroupPersistence groupPersistence,
        EndpointPersistence endpointPersistence) {
        this.groupPersistence = groupPersistence;
        this.endpointPersistence = endpointPersistence;
    }

    public Tuple2<JwtGroup, RestfulEndpoint> addRestfulToJwtGroup(GroupId id,
        EndpointId endpointId) {
        var group = groupPersistence.findJwtById(id);
        var restfulEndpoint = endpointPersistence.findRestfulById(endpointId);
        var newEndpoint = group.add(restfulEndpoint);
        groupPersistence.saveJwt(group);
        return Tuple.of(group, newEndpoint);
    }

}
