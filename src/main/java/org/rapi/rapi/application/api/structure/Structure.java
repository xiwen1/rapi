package org.rapi.rapi.application.api.structure;

import lombok.Getter;
import org.rapi.rapi.application.api.structure.schema.ObjectSchema;
import org.rapi.rapi.application.api.structure.schema.Schema;
import org.rapi.rapi.sharedkernel.Entity;

@Getter
public class Structure implements Entity<StructureId> {

    private final StructureId id;
    private Schema schema;
    private String name;

    private Structure(StructureId id, Schema schema, String name) {
        this.id = id;
        this.schema = schema;
        this.name = name;
    }

    public static Structure fromRaw(StructureId id, Schema schema, String name) {
        return new Structure(id, schema, name);
    }

    public static Structure create() {
        return new Structure(StructureId.create(), ObjectSchema.create(), "");
    }

    public void updateSchema(Schema schema) {
        this.schema = schema;
    }

    public void rename(String name) {
        this.name = name;
    }
}
