package org.rapi.rapi.presentation.converter;

import java.util.List;
import org.rapi.rapi.application.api.endpoint.route.ConstantFragment;
import org.rapi.rapi.application.api.endpoint.route.Route;
import org.rapi.rapi.application.api.endpoint.route.SchemaFragment;
import org.rapi.rapi.presentation.dto.RestfulEndpointDetailDto;
import org.rapi.rapi.presentation.dto.RestfulEndpointDetailDto.RoutePath;
import org.springframework.stereotype.Service;

@Service
public class DetailRouteConverter {

    private final PresentationSchemaConverter presentationSchemaConverter;

    public DetailRouteConverter(PresentationSchemaConverter presentationSchemaConverter) {
        this.presentationSchemaConverter = presentationSchemaConverter;
    }

    public List<RoutePath> toRoutePath(Route route) {
        var routePaths = route.fragments().map(f -> {
            switch (f) {
                case ConstantFragment constantFragment -> {
                    var ret = new RoutePath();
                    ret.setConstant(constantFragment.constant());
                    return ret;
                }
                case SchemaFragment schemaFragment -> {
                    var ret = new RoutePath();
                    ret.setName(schemaFragment.name());
                    ret.setSchema(
                        presentationSchemaConverter.toSchemaDto(schemaFragment.schema()));
                    return ret;
                }
                default -> throw new IllegalArgumentException("Invalid fragment type");
            }
        });
        return routePaths.toJavaList();
    }

    public Route toRoute(List<RestfulEndpointDetailDto.RoutePath> routePath) {
        return new Route(io.vavr.collection.List.ofAll(routePath).map(r -> {
            if (r.getConstant() != null) {
                return new ConstantFragment(r.getConstant());
            } else {
                return new SchemaFragment(r.getName(),
                    presentationSchemaConverter.fromSchemaDto(r.getSchema()));
            }
        }));
    }
}