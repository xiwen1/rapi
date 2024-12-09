package org.rapi.rapi.application.api.infrastructure.dto.schema;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;

@TypeAlias("listSchema")
@Getter
@Setter
public class ListSchemaDto implements SchemaDto {

    private SchemaDto item;
}
