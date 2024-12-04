package org.rapi.rapi.application.api.endpoint;

import lombok.Getter;
import org.rapi.rapi.application.api.structure.schema.Schema;

@Getter
public class GrpcEndpoint extends Endpoint {
    private String service;
    private boolean isParamStream;
    private boolean isResultStream;
    private Schema param;
    private Schema result;

    private GrpcEndpoint(EndpointId id, String title, String description, Schema request, String service, boolean isParamStream, boolean isResultStream, Schema param, Schema result) {
        super(id, title, description, request);
        this.service = service;
        this.isParamStream = isParamStream;
        this.isResultStream = isResultStream;
        this.param = param;
        this.result = result;
    }

    public static GrpcEndpoint create(EndpointId id, String title, String description, Schema request, String service, boolean isParamStream, boolean isResultStream, Schema param, Schema result) {
        return new GrpcEndpoint(id, title, description, request, service, isParamStream, isResultStream, param, result);
    }

    public static GrpcEndpoint create(String title, String description, Schema request, String service, boolean isParamStream, boolean isResultStream, Schema param, Schema result) {
        return new GrpcEndpoint(EndpointId.create(), title, description, request, service, isParamStream, isResultStream, param, result);
    }
}
