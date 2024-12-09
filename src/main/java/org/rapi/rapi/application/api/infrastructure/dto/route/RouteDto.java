package org.rapi.rapi.application.api.infrastructure.dto.route;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.rapi.rapi.application.api.endpoint.route.Fragment;

@Getter
@Setter
public class RouteDto {

    private List<Fragment> fragments;
}
