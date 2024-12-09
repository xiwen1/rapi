package org.rapi.rapi.application.api.infrastructure.dto;


import io.vavr.control.Option;
import lombok.Getter;
import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.group.CrudGroup;
import org.rapi.rapi.application.api.group.GroupId;
import org.rapi.rapi.application.api.structure.StructureId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document
public class CrudGroupDto {

    @Id
    private String _id;
    private final StructureId source;
    private final EndpointId createEndpointId;
    private final EndpointId listEndpointId;
    private final EndpointId updateEndpointId;
    private final EndpointId deleteEndpointId;

    public CrudGroupDto(String _id, StructureId source, EndpointId createEndpointId,
        EndpointId listEndpointId, EndpointId updateEndpointId, EndpointId deleteEndpointId) {
        this._id = _id;
        this.source = source;
        this.createEndpointId = createEndpointId;
        this.listEndpointId = listEndpointId;
        this.updateEndpointId = updateEndpointId;
        this.deleteEndpointId = deleteEndpointId;
    }

    public static CrudGroupDto fromDomain(CrudGroup domain) {
        return new CrudGroupDto(
            domain.getId().id().toString(),
            domain.getSource().getOrNull(),
            domain.getCreateEndpointId().getOrNull(),
            domain.getListEndpointId().getOrNull(),
            domain.getUpdateEndpointId().getOrNull(),
            domain.getDeleteEndpointId().getOrNull()
        );
    }

    public CrudGroup toDomain() {
        return CrudGroup.fromRaw(
            GroupId.fromString(get_id()),
            source == null ? Option.none() : Option.some(source),
            createEndpointId == null ? Option.none() : Option.some(createEndpointId),
            listEndpointId == null ? Option.none() : Option.some(listEndpointId),
            updateEndpointId == null ? Option.none() : Option.some(updateEndpointId),
            deleteEndpointId == null ? Option.none() : Option.some(deleteEndpointId)
        );
    }
}
