package org.rapi.rapi.application.api.infrastructure.dto;

import io.vavr.collection.List;
import lombok.Getter;
import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.group.GroupId;
import org.rapi.rapi.application.api.inventory.Inventory;
import org.rapi.rapi.application.api.inventory.InventoryId;
import org.rapi.rapi.application.api.structure.StructureId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document("inventory")
public class InventoryDto {

    @Id
    private String _id;
    private final List<StructureId> structures;
    private final List<GroupId> jwtGroups;
    private final List<GroupId> crudGroups;
    private final List<EndpointId> restfulEndpoints;
    private final List<EndpointId> grpcEndpoints;

    public InventoryDto(String _id, List<StructureId> structures, List<GroupId> jwtGroups,
        List<GroupId> crudGroups, List<EndpointId> restfulEndpoints,
        List<EndpointId> grpcEndpoints) {
        this._id = _id;
        this.structures = structures;
        this.jwtGroups = jwtGroups;
        this.crudGroups = crudGroups;
        this.restfulEndpoints = restfulEndpoints;
        this.grpcEndpoints = grpcEndpoints;
    }

    public static InventoryDto fromDomain(Inventory domain) {
        return new InventoryDto(
            domain.getId().id().toString(),
            domain.getStructures(),
            domain.getJwtGroups(),
            domain.getCrudGroups(),
            domain.getRestfulEndpoints(),
            domain.getGrpcEndpoints()
        );
    }

    public Inventory toDomain() {
        return Inventory.fromRaw(
            InventoryId.fromString(get_id()),
            structures,
            jwtGroups,
            crudGroups,
            restfulEndpoints,
            grpcEndpoints
        );
    }
}
