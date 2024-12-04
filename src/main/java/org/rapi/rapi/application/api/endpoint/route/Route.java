package org.rapi.rapi.application.api.endpoint.route;

import io.vavr.collection.List;

public record Route(List<Fragment> fragments) {

    public static Route create(List<Fragment> fragments) {
        return new Route(fragments);
    }

    public static Route create(Fragment... fragments) {
        return new Route(List.of(fragments));
    }

    public Route add(Fragment fragment) {
        return new Route(fragments.append(fragment));
    }
}
