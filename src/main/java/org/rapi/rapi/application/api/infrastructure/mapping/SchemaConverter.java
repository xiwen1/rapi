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

    private final SchemaConverter schemaConverter;
    private final VavrMapConverter vavrMapConverter;
    private final UuidConverter uuidConverter;

    public SchemaConverter(SchemaConverter schemaConverter, VavrMapConverter vavrMapConverter,
        UuidConverter uuidConverter) {
        this.schemaConverter = schemaConverter;
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
                schemaDto.setListItem(schemaConverter.toSchemaDto(listSchema.item()));
                return schemaDto;
            }
            case ObjectSchema objectSchema -> {
                var schemaDto = new SchemaDto();
                schemaDto.setType("object");
                schemaDto.setObjectFields(vavrMapConverter.toMap(
                    objectSchema.fields().mapValues(schemaConverter::toSchemaDto)));
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
                    .mapValues(schemaConverter::fromSchemaDto));
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
