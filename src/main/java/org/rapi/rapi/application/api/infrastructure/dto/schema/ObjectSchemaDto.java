package org.rapi.rapi.application.api.infrastructure.dto.schema;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;

@TypeAlias("objectSchema")
@Getter
@Setter
public class ObjectSchemaDto implements SchemaDto {

    private Map<String, SchemaDto> fields;
}
