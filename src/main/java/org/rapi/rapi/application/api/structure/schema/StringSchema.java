package org.rapi.rapi.application.api.structure.schema;

public record StringSchema() implements Schema {
    public static StringSchema create() {
        return new StringSchema();
    }
}
