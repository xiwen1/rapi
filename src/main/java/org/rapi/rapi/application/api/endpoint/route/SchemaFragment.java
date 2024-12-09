package org.rapi.rapi.application.api.endpoint.route;

import org.rapi.rapi.application.api.structure.schema.Schema;

public record SchemaFragment(String name, Schema schema) implements Fragment {

    public static SchemaFragment create(String name, Schema schema) {
        return new SchemaFragment(name, schema);
    }
}
