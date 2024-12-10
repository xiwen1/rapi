package org.rapi.rapi.application.api.structure.schema;

public sealed interface Schema permits ListSchema, NumberSchema, ObjectSchema, RefSchema,
    StringSchema {

}
