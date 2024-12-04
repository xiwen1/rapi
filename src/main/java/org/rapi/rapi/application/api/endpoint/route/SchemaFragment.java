package org.rapi.rapi.application.api.endpoint.route;

import org.rapi.rapi.application.api.structure.schema.Schema;

public record SchemaFragment(Schema schema) implements Fragment {
    public SchemaFragment {
        if (schema == null) {
            throw new IllegalArgumentException("Schema cannot be null");
        }
    }
}
