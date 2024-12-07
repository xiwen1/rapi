package org.rapi.rapi.application.api.endpoint;

import lombok.Getter;
import org.rapi.rapi.application.api.structure.schema.Schema;

@Getter
public class GrpcEndpoint extends Endpoint {

    private final String service;
    private final boolean isParamStream;
    private final boolean isResultStream;
    private final Schema param;
    private final Schema result;

    private GrpcEndpoint(EndpointId id, String title, String description,
        String service,
        boolean isParamStream, boolean isResultStream, Schema param, Schema result) {
        super(id, title, description);
        this.service = service;
        this.isParamStream = isParamStream;
        this.isResultStream = isResultStream;
        this.param = param;
        this.result = result;
    }

    public static GrpcEndpoint fromRaw(EndpointId id, String title, String description,
        String service,
        boolean isParamStream, boolean isResultStream, Schema param, Schema result) {
        return new GrpcEndpoint(id, title, description, service, isParamStream,
            isResultStream,
            param, result);
    }

    public static GrpcEndpoint create(String title, String description,
        String service,
        boolean isParamStream, boolean isResultStream, Schema param, Schema result) {
        return new GrpcEndpoint(EndpointId.create(), title, description, service,
            isParamStream,
            isResultStream, param, result);
    }
}
