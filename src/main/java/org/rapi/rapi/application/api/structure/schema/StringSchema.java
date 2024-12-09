package org.rapi.rapi.application.api.structure.schema;

import org.springframework.data.annotation.TypeAlias;

@TypeAlias("stringSchema")
public record StringSchema() implements Schema {

    public static StringSchema create() {
        return new StringSchema();
    }
}
