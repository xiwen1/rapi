package org.rapi.rapi.application.api.infrastructure.dto;

import lombok.Getter;
import lombok.Setter;
import org.rapi.rapi.application.api.structure.schema.Schema;

@Getter
@Setter
public class ResponseDto {

    private int statusCode;
    private String description;
    private Schema schema;
}
