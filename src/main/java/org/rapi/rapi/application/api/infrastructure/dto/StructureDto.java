package org.rapi.rapi.application.api.infrastructure.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("structure")
public class StructureDto {

    @Id
    private String id;
    private SchemaDto schema;
    private String name;
}
