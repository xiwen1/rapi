package org.rapi.rapi.application.api.endpoint.route;

import java.util.List;

public record Route(List<Fragment> fragments) {
    public Route {
        if (fragments == null) {
            throw new IllegalArgumentException("fragments cannot be null");
        }
    }
}
