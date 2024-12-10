package org.rapi.rapi.application.api.infrastructure.mapping;

import org.springframework.stereotype.Service;

@Service
public class VavrOptionNullConverter {

    public <T> io.vavr.control.Option<T> fromNullable(T value) {
        return value == null ? io.vavr.control.Option.none() : io.vavr.control.Option.some(value);
    }

    public <T> T toNullable(io.vavr.control.Option<T> option) {
        return option.getOrElse(() -> null);
    }
}
