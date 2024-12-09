package org.rapi.rapi.application.api.infrastructure;

import static java.util.Arrays.asList;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

@Configuration
public class EntityConverter {

    @Bean
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(asList(
            new VavrMapToMapConverter(),
            new MapToVavrMapConverter()
//            new VavrListToListConverter(),
//            new ListToVavrListConverter()
        ));
    }

    @WritingConverter
    private static class VavrMapToMapConverter implements Converter<Map, java.util.Map> {

        @Override
        public java.util.Map convert(Map map) {
            return map.toJavaMap();
        }
    }

    @ReadingConverter
    private static class MapToVavrMapConverter implements Converter<java.util.Map, Map> {

        @Override
        public Map convert(java.util.Map map) {
            return HashMap.ofAll(map);
        }
    }

//    @WritingConverter
//    private static class VavrListToListConverter implements Converter<io.vavr.collection.List, java.util.List> {
//        @Override
//        public java.util.List convert(io.vavr.collection.List list) {
//            return list.asJava();
//        }
//    }
//
//    @WritingConverter
//    private static class ListToVavrListConverter implements Converter<java.util.List, io.vavr.collection.List> {
//        @Override
//        public io.vavr.collection.List convert(java.util.List list) {
//            return io.vavr.collection.List.ofAll(list);
//        }
//    }


}
