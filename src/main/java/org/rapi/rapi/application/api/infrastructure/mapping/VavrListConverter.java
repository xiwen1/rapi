package org.rapi.rapi.application.api.infrastructure.mapping;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class VavrListConverter {

    public <T> List<T> toList(io.vavr.collection.List<T> vavrList) {
        return vavrList.asJava();
    }

    public <T> io.vavr.collection.List<T> fromList(List<T> list) {
        return io.vavr.collection.List.ofAll(list);
    }
}
