package org.rapi.rapi.application.api.infrastructure.dto;


import lombok.Getter;
import org.rapi.rapi.application.api.structure.Structure;
import org.rapi.rapi.application.api.structure.StructureId;
import org.rapi.rapi.application.api.structure.schema.Schema;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document("structure")
public class StructureDto {

    @Id
    private String _id;
    private final Schema schema;
    private final String name;

    public StructureDto(String _id, Schema schema, String name) {
        this._id = _id;
        this.schema = schema;
        this.name = name;
    }

    public static StructureDto fromDomain(Structure domain) {
        return new StructureDto(
            domain.getId().id().toString(),
            domain.getSchema(),
            domain.getName()
        );
    }

    public Structure toDomain() {
        return Structure.fromRaw(
            StructureId.fromString(get_id()),
            schema,
            name
        );
    }
}
