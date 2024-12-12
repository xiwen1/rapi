package org.rapi.rapi.presentation.dto;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchemaDto {

    private String type;
    private SchemaDto item;
    private Map<String, SchemaDto> fields;
    private String ref;
}
