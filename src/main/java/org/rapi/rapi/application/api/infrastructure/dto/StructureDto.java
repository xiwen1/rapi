package org.rapi.rapi.application.api.infrastructure.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StructureDto {

    private String id;
    private SchemaDto schema;
    private String name;
}
