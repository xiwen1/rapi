package org.rapi.rapi.application.api.infrastructure.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FragmentDto {

    private String type;
    private String constant;
    private String name;
    private SchemaDto schema;
}
