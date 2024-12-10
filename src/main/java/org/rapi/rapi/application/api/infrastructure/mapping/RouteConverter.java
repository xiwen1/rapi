package org.rapi.rapi.application.api.infrastructure.mapping;

import org.rapi.rapi.application.api.endpoint.route.Route;
import org.rapi.rapi.application.api.infrastructure.dto.RouteDto;
import org.springframework.stereotype.Service;

@Service
public class RouteConverter {

    private final VavrListConverter vavrListConverter;
    private final FragmentConverter fragmentConverter;

    public RouteConverter(VavrListConverter vavrListConverter,
        FragmentConverter fragmentConverter) {
        this.vavrListConverter = vavrListConverter;
        this.fragmentConverter = fragmentConverter;
    }

    public RouteDto toRouteDto(Route route) {
        var routeDto = new RouteDto();
        routeDto.setFragments(
            vavrListConverter.toList(route.fragments().map(fragmentConverter::toFragmentDto)));
        return routeDto;
    }

    public Route fromRouteDto(RouteDto routeDto) {
        return new Route(
            vavrListConverter.fromList(routeDto.getFragments())
                .map(fragmentConverter::fromFragmentDto)
        );
    }
}
