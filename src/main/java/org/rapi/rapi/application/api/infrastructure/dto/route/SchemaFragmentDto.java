package org.rapi.rapi.application.api.infrastructure.dto.route;

import lombok.Getter;
import lombok.Setter;
import org.rapi.rapi.application.api.structure.schema.Schema;
import org.springframework.data.annotation.TypeAlias;

@TypeAlias("schemaFragment")
@Getter
@Setter
public class SchemaFragmentDto implements FragmentDto {

    private String name;
    private Schema schema;
}
