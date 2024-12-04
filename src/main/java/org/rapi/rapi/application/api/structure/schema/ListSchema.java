package org.rapi.rapi.application.api.structure.schema;

public record ListSchema(Schema item) implements Schema {

    public static ListSchema create(Schema item) {
        return new ListSchema(item);
    }
}
