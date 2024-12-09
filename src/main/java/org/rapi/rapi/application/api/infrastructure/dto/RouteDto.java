package org.rapi.rapi.application.api.infrastructure.dto;

import io.vavr.collection.List;
import org.rapi.rapi.application.api.endpoint.route.Fragment;
import org.rapi.rapi.application.api.endpoint.route.Route;
import org.springframework.data.annotation.PersistenceCreator;

public class RouteDto {

    private final java.util.List<Fragment> fragments;

    @PersistenceCreator
    public RouteDto(java.util.List<Fragment> fragments) {
        this.fragments = fragments;
    }

    public static RouteDto fromDomain(Route domain) {
        return new RouteDto(
            domain.fragments().toJavaList()
        );
    }

    public Route toDomain() {
        return Route.create(List.ofAll(fragments));
    }
}
