package org.rapi.rapi.application.api.inventory;

import io.vavr.collection.List;
import lombok.Getter;
import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.group.GroupId;
import org.rapi.rapi.application.api.structure.StructureId;
import org.rapi.rapi.sharedkernel.Entity;

@Getter
public class Inventory implements Entity<InventoryId> {

    private final InventoryId id;
    private List<StructureId> structures;
    private List<GroupId> groups;
    private List<EndpointId> endpoints;

    private Inventory(InventoryId id, List<StructureId> structures, List<GroupId> groups,
        List<EndpointId> endpoints) {
        this.id = id;
        this.structures = structures;
        this.groups = groups;
        this.endpoints = endpoints;
    }

    public static Inventory create(InventoryId id, List<StructureId> structures,
        List<GroupId> groups, List<EndpointId> endpoints) {
        return new Inventory(id, structures, groups, endpoints);
    }

    public static Inventory create(List<StructureId> structures, List<GroupId> groups,
        List<EndpointId> endpoints) {
        return new Inventory(InventoryId.create(), structures, groups, endpoints);
    }

    public static Inventory create() {
        return new Inventory(InventoryId.create(), List.empty(), List.empty(), List.empty());
    }

    public void addStructure(StructureId structureId) {
        if (structures.contains(structureId)) {
            throw new IllegalArgumentException("Structure already exists in inventory");
        }
        structures = structures.append(structureId);
    }

    public void addGroup(GroupId groupId) {
        if (groups.contains(groupId)) {
            throw new IllegalArgumentException("Group already exists in inventory");
        }
        groups = groups.append(groupId);
    }

    public void addEndpoint(EndpointId endpointId) {
        if (endpoints.contains(endpointId)) {
            throw new IllegalArgumentException("Endpoint already exists in inventory");
        }
        endpoints = endpoints.append(endpointId);
    }

    public void removeStructure(StructureId structureId) {
        if (!structures.contains(structureId)) {
            throw new IllegalArgumentException("Structure does not exist in inventory");
        }
        structures = structures.remove(structureId);
    }

    public void removeGroup(GroupId groupId) {
        if (!groups.contains(groupId)) {
            throw new IllegalArgumentException("Group does not exist in inventory");
        }
        groups = groups.remove(groupId);
    }

    public void removeEndpoint(EndpointId endpointId) {
        if (!endpoints.contains(endpointId)) {
            throw new IllegalArgumentException("Endpoint does not exist in inventory");
        }
        endpoints = endpoints.remove(endpointId);
    }
}
