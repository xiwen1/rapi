package org.rapi.rapi.application.api.infrastructure.mapping;

import io.vavr.collection.List;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.ValidationException;
import org.modelmapper.config.Configuration.AccessLevel;
import org.rapi.rapi.application.api.infrastructure.dto.schema.ListSchemaDto;
import org.rapi.rapi.application.api.infrastructure.dto.schema.NumberSchemaDto;
import org.rapi.rapi.application.api.infrastructure.dto.schema.ObjectSchemaDto;
import org.rapi.rapi.application.api.infrastructure.dto.schema.SchemaDto;
import org.rapi.rapi.application.api.infrastructure.dto.schema.StringSchemaDto;
import org.rapi.rapi.application.api.structure.schema.ListSchema;
import org.rapi.rapi.application.api.structure.schema.NumberSchema;
import org.rapi.rapi.application.api.structure.schema.ObjectSchema;
import org.rapi.rapi.application.api.structure.schema.Schema;
import org.rapi.rapi.application.api.structure.schema.StringSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;

@Configuration
public class ApiModelMapperConfig {

    private static void addMapConverter(ModelMapper modelMapper) {
        // add converters for Vavr Map <-> Java Map
        Converter<io.vavr.collection.Map<?, ?>, java.util.Map<?, ?>> vavrToJavaMapConverter = context ->
            context.getSource() == null
                ? new java.util.HashMap<>()
                : context.getSource().toJavaMap();
        modelMapper.addConverter(vavrToJavaMapConverter);

        Converter<java.util.Map<?, ?>, io.vavr.collection.Map<?, ?>> javaToVavrMapConverter = context ->
            context.getSource() == null
                ? io.vavr.collection.HashMap.empty()
                : io.vavr.collection.HashMap.ofAll(context.getSource());
        modelMapper.addConverter(javaToVavrMapConverter);
    }

    private static void addUuidStringConverter(ModelMapper modelMapper) {
        // add converters for UUID <-> String
        Converter<java.util.UUID, String> uuidToStringConverter = context ->
            context.getSource() == null
                ? null
                : context.getSource().toString();
        modelMapper.addConverter(uuidToStringConverter);

        Converter<String, java.util.UUID> stringToUuidConverter = context ->
            context.getSource() == null
                ? null
                : java.util.UUID.fromString(context.getSource());
        modelMapper.addConverter(stringToUuidConverter);
    }

    private static void addListConverter(ModelMapper modelMapper) {
        // add converters for Vavr List <-> Java List
        Converter<List<?>, java.util.List<?>> vavrToJavaListConverter = context ->
            context.getSource() == null
                ? new java.util.ArrayList<>()
                : context.getSource().toJavaList();
        modelMapper.addConverter(vavrToJavaListConverter);

        Converter<java.util.List<?>, List<?>> javaToVavrListConverter = context ->
            context.getSource() == null
                ? List.empty()
                : List.ofAll(context.getSource());
        modelMapper.addConverter(javaToVavrListConverter);
    }

    private static void addHttpMethodStringConverter(ModelMapper modelMapper) {
        // add converters for HttpMethod <-> String
        Converter<HttpMethod, String> httpMethodToStringConverter = context ->
            context.getSource() == null
                ? null
                : context.getSource().name();
        modelMapper.addConverter(httpMethodToStringConverter);

        Converter<String, HttpMethod> stringToHttpMethodConverter = context ->
            context.getSource() == null
                ? null
                : HttpMethod.valueOf(context.getSource());
        modelMapper.addConverter(stringToHttpMethodConverter);
    }

    private static void addOptionNullConverter(ModelMapper modelMapper) {
        // add converters for Option <-> Null
        Converter<io.vavr.control.Option<?>, Object> optionToNullConverter = context ->
            context.getSource() == null || context.getSource().isEmpty()
                ? null
                : context.getSource().get();
        modelMapper.addConverter(optionToNullConverter);

        Converter<Object, io.vavr.control.Option<?>> nullToOptionConverter = context ->
            context.getSource() == null
                ? io.vavr.control.Option.none()
                : io.vavr.control.Option.some(context.getSource());
        modelMapper.addConverter(nullToOptionConverter);
    }

    private static void addHttpStatusCodeIntegerConverter(ModelMapper modelMapper) {
        // add converters for HttpStatus <-> Integer
        Converter<HttpStatusCode, Integer> httpStatusToIntegerConverter = context ->
            context.getSource() == null
                ? null
                : context.getSource().value();
        modelMapper.addConverter(httpStatusToIntegerConverter);

        Converter<Integer, HttpStatusCode> integerToHttpStatusConverter = context ->
            context.getSource() == null
                ? null
                : HttpStatusCode.valueOf(context.getSource());
        modelMapper.addConverter(integerToHttpStatusConverter);
    }

    @Bean
    public ModelMapper apiModelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
            .setFieldMatchingEnabled(true)
            .setFieldAccessLevel(AccessLevel.PRIVATE);

        modelMapper.typeMap(SchemaDto.class, Schema.class)
            .include(ObjectSchemaDto.class, ObjectSchema.class)
            .include(StringSchemaDto.class, StringSchema.class)
            .include(ListSchemaDto.class, ListSchema.class)
            .include(NumberSchemaDto.class, NumberSchema.class)
            .include(ListSchemaDto.class, ListSchema.class);

        addListConverter(modelMapper);
        addUuidStringConverter(modelMapper);
        addMapConverter(modelMapper);
        addHttpMethodStringConverter(modelMapper);
        addOptionNullConverter(modelMapper);
        addHttpStatusCodeIntegerConverter(modelMapper);

        // validate the model mapper configuration
        try {
            modelMapper.validate();
        } catch (ValidationException e) {
            throw new IllegalStateException("ModelMapper configuration error: " + e.getMessage());
        }

        return new ModelMapper();
    }
}
