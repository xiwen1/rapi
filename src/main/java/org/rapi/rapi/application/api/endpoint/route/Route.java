package org.rapi.rapi.application.api.endpoint.route;


import org.springframework.lang.NonNull;

import java.util.List;

public record Route(List<Fragment> fragments) {
}
