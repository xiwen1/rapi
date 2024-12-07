package org.rapi.rapi.application.api.group.effectfulbehavior;

import org.rapi.rapi.application.api.group.CrudGroup;
import org.rapi.rapi.application.api.group.GroupId;
import org.rapi.rapi.application.api.group.JwtGroup;

public interface GroupPersistence {

    void saveCrud(CrudGroup crudGroup);

    void saveJwt(JwtGroup jwtGroup);

    void delete(GroupId groupId);

    CrudGroup findCrudById(GroupId id);

    JwtGroup findJwtById(GroupId id);

}
