package org.rapi.rapi.presentation.converter;

import org.springframework.stereotype.Service;

@Service
public class PresentationVavrMapConverter {

    public <K, V> java.util.Map<K, V> toMap(io.vavr.collection.Map<K, V> vavrMap) {
        return vavrMap.toJavaMap();
    }

    public <K, V> io.vavr.collection.Map<K, V> fromMap(java.util.Map<K, V> map) {
        return io.vavr.collection.HashMap.ofAll(map);
    }
}
