package org.rapi.rapi.application.api.service.query;

import io.vavr.collection.List;
import org.rapi.rapi.application.api.endpoint.RestfulEndpoint;
import org.rapi.rapi.application.api.group.GroupId;
import org.rapi.rapi.application.api.service.EndpointPersistence;
import org.rapi.rapi.application.api.service.GroupPersistence;
import org.springframework.stereotype.Service;

@Service
public class GetJwtGroupSourceEndpointsQuery {

    private final GroupPersistence groupPersistence;
    private final EndpointPersistence endpointPersistence;

    public GetJwtGroupSourceEndpointsQuery(GroupPersistence groupPersistence,
        EndpointPersistence endpointPersistence) {
        this.groupPersistence = groupPersistence;
        this.endpointPersistence = endpointPersistence;
    }

    public List<RestfulEndpoint> getJwtGroupSourceEndpoints(GroupId groupId) {
        var group = groupPersistence.findJwtById(groupId);
        return group.getProtectedEndpointsMap().keySet().toList()
            .map(endpointPersistence::findRestfulById);
    }
}
