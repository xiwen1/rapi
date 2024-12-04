package org.rapi.rapi.application.api.endpoint.route;

import org.rapi.rapi.application.api.structure.schema.Schema;

public record SchemaFragment(Schema schema) implements Fragment {

    public static SchemaFragment create(Schema schema) {
        return new SchemaFragment(schema);
    }
}
