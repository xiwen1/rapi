package org.rapi.rapi.application.api.group.effectfulbehavior;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.rapi.rapi.application.api.group.CrudGroup;
import org.rapi.rapi.application.api.group.CrudGroup.CrudEndpoints;
import org.rapi.rapi.application.api.group.GroupId;
import org.rapi.rapi.application.api.structure.StructureId;
import org.rapi.rapi.application.api.structure.effectfulbehavior.StructurePersistence;

public class SetCrudGroupBehavior {

    private final GroupPersistence groupPersistence;
    private final StructurePersistence structurePersistence;


    public SetCrudGroupBehavior(GroupPersistence groupPersistence,
        StructurePersistence structurePersistence) {
        this.groupPersistence = groupPersistence;
        this.structurePersistence = structurePersistence;
    }

    public Tuple2<CrudGroup, CrudEndpoints> setCrudGroup(GroupId groupId, StructureId structureId) {
        var group = groupPersistence.findCrudById(groupId);
        var endpoints = group.set(structurePersistence.findById(structureId));
        groupPersistence.saveCrud(group);
        return Tuple.of(group, endpoints);
    }

}
