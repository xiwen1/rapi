package org.rapi.rapi.application.api.infrastructure.dto.schema;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;

@TypeAlias("refSchema")
@Getter
@Setter
public class RefSchemaDto implements SchemaDto {

    private UUID reference;
}