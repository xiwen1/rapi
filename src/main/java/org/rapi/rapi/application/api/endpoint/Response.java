package org.rapi.rapi.application.api.endpoint;

import org.rapi.rapi.application.api.structure.schema.ObjectSchema;
import org.rapi.rapi.application.api.structure.schema.Schema;
import org.springframework.http.HttpStatusCode;

import java.util.HashMap;

public record Response(HttpStatusCode statusCode, String description, Schema schema) {
    public Response {
        if (statusCode == null) {
            throw new IllegalArgumentException("statusCode cannot be null");
        }
        if (description == null) {
            throw new IllegalArgumentException("description cannot be null");
        }
        if (schema == null) {
            throw new IllegalArgumentException("schema cannot be null");
        }
    }

    public static Response create(HttpStatusCode statusCode, String description, Schema schema) {
        return new Response(statusCode, description, schema);
    }

    public static Response create(HttpStatusCode statusCode, String description) {
        return new Response(statusCode, description, new ObjectSchema(new HashMap<>()));
    }

    public static Response create() {
        return new Response(HttpStatusCode.valueOf(200), "", new ObjectSchema(new HashMap<>()));
    }
}
