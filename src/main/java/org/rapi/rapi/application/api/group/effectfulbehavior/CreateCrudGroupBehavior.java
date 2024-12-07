package org.rapi.rapi.application.api.group.effectfulbehavior;

import org.rapi.rapi.application.api.group.CrudGroup;

public class CreateCrudGroupBehavior {

    private final GroupPersistence groupPersistence;

    public CreateCrudGroupBehavior(GroupPersistence groupPersistence) {
        this.groupPersistence = groupPersistence;
    }

    public CrudGroup createCrudGroup() {
        var group = CrudGroup.create();
        groupPersistence.saveCrud(group);
        return group;
    }


}
