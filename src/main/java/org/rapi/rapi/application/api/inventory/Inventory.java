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
    private List<GroupId> jwtGroups;
    private List<GroupId> crudGroups;
    private List<EndpointId> restfulEndpoints;
    private List<EndpointId> grpcEndpoints;

    private Inventory(InventoryId id, List<StructureId> structures,
        List<GroupId> jwtGroups, List<GroupId> crudGroups, List<EndpointId> restfulEndpoints,
        List<EndpointId> grpcEndpoints) {
        this.id = id;
        this.structures = structures;
        this.jwtGroups = jwtGroups;
        this.crudGroups = crudGroups;
        this.restfulEndpoints = restfulEndpoints;
        this.grpcEndpoints = grpcEndpoints;
    }

    public static Inventory fromRaw(InventoryId id, List<StructureId> structures,
        List<GroupId> jwtGroups, List<GroupId> crudGroups, List<EndpointId> restfulEndpoints,
        List<EndpointId> grpcEndpoints) {
        return new Inventory(id, structures, jwtGroups, crudGroups, restfulEndpoints,
            grpcEndpoints);
    }

    public static Inventory create() {
        return new Inventory(InventoryId.create(), List.empty(), List.empty(), List.empty(),
            List.empty(), List.empty());
    }

    public void addStructure(StructureId structureId) {
        if (structures.contains(structureId)) {
            throw new IllegalArgumentException("Structure already exists in inventory");
        }
        structures = structures.append(structureId);
    }

    public void addJwtGroup(GroupId groupId) {
        if (jwtGroups.contains(groupId)) {
            throw new IllegalArgumentException("Group already exists in inventory");
        }
        jwtGroups = jwtGroups.append(groupId);
    }

    public void addCrudGroup(GroupId groupId) {
        if (crudGroups.contains(groupId)) {
            throw new IllegalArgumentException("Group already exists in inventory");
        }
        crudGroups = crudGroups.append(groupId);
    }

    public void addRestfulEndpoint(EndpointId endpointId) {
        if (restfulEndpoints.contains(endpointId)) {
            throw new IllegalArgumentException("Endpoint already exists in inventory");
        }
        restfulEndpoints = restfulEndpoints.append(endpointId);
    }

    public void addGrpcEndpoint(EndpointId endpointId) {
        if (grpcEndpoints.contains(endpointId)) {
            throw new IllegalArgumentException("Endpoint already exists in inventory");
        }
        grpcEndpoints = grpcEndpoints.append(endpointId);
    }

    public void removeStructure(StructureId structureId) {
        if (!structures.contains(structureId)) {
            throw new IllegalArgumentException("Structure does not exist in inventory");
        }
        structures = structures.remove(structureId);
    }

    public void removeJwtGroup(GroupId groupId) {
        if (!jwtGroups.contains(groupId)) {
            throw new IllegalArgumentException("Group does not exist in inventory");
        }
        jwtGroups = jwtGroups.remove(groupId);
    }

    public void removeCrudGroup(GroupId groupId) {
        if (!crudGroups.contains(groupId)) {
            throw new IllegalArgumentException("Group does not exist in inventory");
        }
        crudGroups = crudGroups.remove(groupId);
    }

    public void removeRestfulEndpoint(EndpointId endpointId) {
        if (!restfulEndpoints.contains(endpointId)) {
            throw new IllegalArgumentException("Endpoint does not exist in inventory");
        }
        restfulEndpoints = restfulEndpoints.remove(endpointId);
    }

    public void removeGrpcEndpoint(EndpointId endpointId) {
        if (!grpcEndpoints.contains(endpointId)) {
            throw new IllegalArgumentException("Endpoint does not exist in inventory");
        }
        grpcEndpoints = grpcEndpoints.remove(endpointId);
    }
}
