package org.rapi.rapi.application.api.structure.schema;


public record NumberSchema() implements Schema {

    public static NumberSchema create() {
        return new NumberSchema();
    }
}
