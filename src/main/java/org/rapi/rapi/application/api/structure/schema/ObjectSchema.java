package org.rapi.rapi.application.api.structure.schema;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;

public record ObjectSchema(Map<String, Schema> fields) implements Schema {

    public static ObjectSchema create() {
        return new ObjectSchema(HashMap.empty());
    }

    public ObjectSchema addField(String name, Schema schema) {
        return new ObjectSchema(fields.put(name, schema));
    }
}
