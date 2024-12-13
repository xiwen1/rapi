package org.rapi.rapi.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SchemaDto {

    private String type;
    private SchemaDto item;
    private Map<String, SchemaDto> fields;
    private String ref;
}
