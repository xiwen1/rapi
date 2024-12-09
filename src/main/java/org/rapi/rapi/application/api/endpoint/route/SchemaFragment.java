package org.rapi.rapi.application.api.endpoint.route;

import org.rapi.rapi.application.api.structure.schema.Schema;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@TypeAlias("schemaFragment")
public record SchemaFragment(String name, Schema schema) implements Fragment {

    public static SchemaFragment create(String name, Schema schema) {
        return new SchemaFragment(name, schema);
    }
}
