package org.rapi.rapi.application.api.infrastructure.dto;


import lombok.Getter;
import lombok.Setter;
import org.rapi.rapi.application.api.structure.schema.Schema;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("structure")
@Getter
@Setter
public class StructureDto {

    @Id
    private String id;
    private Schema schema;
    private String name;
}
