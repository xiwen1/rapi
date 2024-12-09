package org.rapi.rapi.application.api.structure.schema;


import org.springframework.data.annotation.TypeAlias;

@TypeAlias("numberSchema")
public record NumberSchema() implements Schema {

    public static NumberSchema create() {
        return new NumberSchema();
    }
}
