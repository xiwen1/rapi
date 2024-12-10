package org.rapi.rapi.application.api.infrastructure.mapping;

import org.rapi.rapi.application.api.infrastructure.dto.SchemaDto;
import org.rapi.rapi.application.api.structure.StructureId;
import org.rapi.rapi.application.api.structure.schema.ListSchema;
import org.rapi.rapi.application.api.structure.schema.NumberSchema;
import org.rapi.rapi.application.api.structure.schema.ObjectSchema;
import org.rapi.rapi.application.api.structure.schema.RefSchema;
import org.rapi.rapi.application.api.structure.schema.Schema;
import org.rapi.rapi.application.api.structure.schema.StringSchema;
import org.springframework.stereotype.Service;

@Service
public class SchemaConverter {

    private final VavrMapConverter vavrMapConverter;
    private final UuidConverter uuidConverter;

    public SchemaConverter(VavrMapConverter vavrMapConverter,
        UuidConverter uuidConverter) {
        this.vavrMapConverter = vavrMapConverter;
        this.uuidConverter = uuidConverter;
    }

    public SchemaDto toSchemaDto(Schema schema) {
        switch (schema) {
            case NumberSchema numberSchema -> {
                var schemaDto = new SchemaDto();
                schemaDto.setType("number");
                return schemaDto;
            }
            case ListSchema listSchema -> {
                var schemaDto = new SchemaDto();
                schemaDto.setType("list");
                schemaDto.setListItem(toSchemaDto(listSchema.item()));
                return schemaDto;
            }
            case ObjectSchema objectSchema -> {
                var schemaDto = new SchemaDto();
                schemaDto.setType("object");
                schemaDto.setObjectFields(vavrMapConverter.toMap(
                    objectSchema.fields().mapValues(this::toSchemaDto)));
                return schemaDto;
            }
            case RefSchema refSchema -> {
                var schemaDto = new SchemaDto();
                schemaDto.setType("ref");
                schemaDto.setStructureReference(uuidConverter.toString(refSchema.reference().id()));
                return schemaDto;
            }
            case StringSchema stringSchema -> {
                var schemaDto = new SchemaDto();
                schemaDto.setType("string");
                return schemaDto;
            }
        }
    }

    public Schema fromSchemaDto(SchemaDto schemaDto) {
        switch (schemaDto.getType()) {
            case "number" -> {
                return new NumberSchema();
            }
            case "list" -> {
                return new ListSchema(fromSchemaDto(schemaDto.getListItem()));
            }
            case "object" -> {
                return new ObjectSchema(vavrMapConverter.fromMap(schemaDto.getObjectFields())
                    .mapValues(this::fromSchemaDto));
            }
            case "ref" -> {
                return new RefSchema(
                    new StructureId(uuidConverter.fromString(schemaDto.getStructureReference())));
            }
            case "string" -> {
                return new StringSchema();
            }
        }
        return null;
    }
}
