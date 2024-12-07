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
        var create = endpointPersistence.findRestfulById(group.getCreateEndpointId()
            .getOrElseThrow(() -> new IllegalStateException("Create endpoint not found")));
        var list = endpointPersistence.findRestfulById(group.getListEndpointId()
            .getOrElseThrow(() -> new IllegalStateException("List endpoint not found")));
        var update = endpointPersistence.findRestfulById(group.getUpdateEndpointId()
            .getOrElseThrow(() -> new IllegalStateException("Update endpoint not found")));
        var delete = endpointPersistence.findRestfulById(group.getDeleteEndpointId()
            .getOrElseThrow(() -> new IllegalStateException("Delete endpoint not found")));
        return group.dissolve(new CrudEndpoints(create, list, update, delete));
    }

}
