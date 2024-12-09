package org.rapi.rapi.application.api.infrastructure.mapping;

import io.vavr.collection.List;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.ValidationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiModelMapperConfig {

    @Bean
    public ModelMapper apiModelMapper() {
        ModelMapper modelMapper = new ModelMapper();

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

        // validate the model mapper configuration
        try {
            modelMapper.validate();
        } catch (ValidationException e) {
            throw new IllegalStateException("ModelMapper configuration error: " + e.getMessage());
        }

        return new ModelMapper();
    }
}
