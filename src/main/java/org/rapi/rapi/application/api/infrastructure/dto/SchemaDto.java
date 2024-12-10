package org.rapi.rapi.application.api.infrastructure.dto;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchemaDto {

    private String type;
    private SchemaDto listItem;
    private Map<String, SchemaDto> objectFields;
    private String structureReference;
}
