package org.rapi.rapi.presentation.converter;


import org.rapi.rapi.application.api.structure.StructureId;
import org.rapi.rapi.application.api.structure.schema.ListSchema;
import org.rapi.rapi.application.api.structure.schema.NumberSchema;
import org.rapi.rapi.application.api.structure.schema.ObjectSchema;
import org.rapi.rapi.application.api.structure.schema.RefSchema;
import org.rapi.rapi.application.api.structure.schema.Schema;
import org.rapi.rapi.application.api.structure.schema.StringSchema;
import org.rapi.rapi.presentation.dto.SchemaDto;
import org.springframework.stereotype.Service;

@Service
public class PresentationSchemaConverter {

    private final PresentationVavrMapConverter presentationVavrMapConverter;
    private final PresentationUuidConverter presentationUuidConverter;

    public PresentationSchemaConverter(PresentationVavrMapConverter presentationVavrMapConverter,
        PresentationUuidConverter presentationUuidConverter) {
        this.presentationVavrMapConverter = presentationVavrMapConverter;
        this.presentationUuidConverter = presentationUuidConverter;
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
                schemaDto.setItem(toSchemaDto(listSchema.item()));
                return schemaDto;
            }
            case ObjectSchema objectSchema -> {
                var schemaDto = new SchemaDto();
                schemaDto.setType("object");
                schemaDto.setFields(presentationVavrMapConverter.toMap(
                    objectSchema.fields().mapValues(this::toSchemaDto)));
                return schemaDto;
            }
            case RefSchema refSchema -> {
                var schemaDto = new SchemaDto();
                schemaDto.setType("ref");
                schemaDto.setRef(presentationUuidConverter.toString(refSchema.reference().id()));
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
                return new ListSchema(fromSchemaDto(schemaDto.getItem()));
            }
            case "object" -> {
                return new ObjectSchema(presentationVavrMapConverter.fromMap(schemaDto.getFields())
                    .mapValues(this::fromSchemaDto));
            }
            case "ref" -> {
                return new RefSchema(
                    new StructureId(presentationUuidConverter.fromString(schemaDto.getRef())));
            }
            case "string" -> {
                return new StringSchema();
            }
        }
        return null;
    }

}
