package org.rapi.rapi.application.api.group.effectfulbehavior;

import org.rapi.rapi.application.api.endpoint.effectfulbehavior.EndpointPersistence;
import org.rapi.rapi.application.api.group.CrudGroup.CrudEndpoints;
import org.rapi.rapi.application.api.group.GroupId;

public class DissolveCrudGroupBehavior {

    private final GroupPersistence groupPersistence;
    private final EndpointPersistence endpointPersistence;

    public DissolveCrudGroupBehavior(GroupPersistence groupPersistence,
        EndpointPersistence endpointPersistence) {
        this.groupPersistence = groupPersistence;
        this.endpointPersistence = endpointPersistence;
    }

    public CrudEndpoints dissolveCrudGroupBehavior(GroupId id) {
        var group = groupPersistence.findCrudById(id);
        groupPersistence.delete(id);
        var endpoints = group.getGeneratedEndpoints()
            .map(endpointPersistence::findRestfulById);
        var dissolvedEndpoints = group.dissolve(CrudEndpoints.fromList(endpoints));
        dissolvedEndpoints.listEndpoints().forEach(endpointPersistence::saveRestful);
        return dissolvedEndpoints;
    }

}
