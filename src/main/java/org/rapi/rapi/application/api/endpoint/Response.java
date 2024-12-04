package org.rapi.rapi.application.api.endpoint;

import org.rapi.rapi.application.api.structure.schema.ObjectSchema;
import org.rapi.rapi.application.api.structure.schema.Schema;
import org.springframework.http.HttpStatusCode;

public record Response(HttpStatusCode statusCode, String description, Schema schema) {

    public static Response create(HttpStatusCode statusCode, String description, Schema schema) {
        return new Response(statusCode, description, schema);
    }

    public static Response create(HttpStatusCode statusCode, String description) {
        return new Response(statusCode, description, ObjectSchema.create());
    }

    public static Response create() {
        return new Response(HttpStatusCode.valueOf(200), "", ObjectSchema.create());
    }
}
