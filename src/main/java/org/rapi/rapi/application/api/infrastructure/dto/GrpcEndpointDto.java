package org.rapi.rapi.application.api.infrastructure.dto;

import java.util.UUID;
import lombok.Getter;
import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.endpoint.GrpcEndpoint;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document("grpc_endpoint")
public class GrpcEndpointDto {

    @Id
    private String _id;
    private final String title;
    private final String description;
    private final String service;
    private final boolean isParamStream;
    private final boolean isResultStream;
    private final String param;
    private final String result;

    public GrpcEndpointDto(String _id, String title, String description, String service,
        boolean isParamStream, boolean isResultStream, String param, String result) {
        this._id = _id;
        this.title = title;
        this.description = description;
        this.service = service;
        this.isParamStream = isParamStream;
        this.isResultStream = isResultStream;
        this.param = param;
        this.result = result;
    }

    public static GrpcEndpointDto fromDomain(GrpcEndpoint domain) {
        return new GrpcEndpointDto(
            domain.getId().id().toString(),
            domain.getTitle(),
            domain.getDescription(),
            domain.getService(),
            domain.isParamStream(),
            domain.isResultStream(),
            SchemaConverter.convertToJson(domain.getParam()),
            SchemaConverter.convertToJson(domain.getResult())
        );
    }

    public GrpcEndpoint toDomain() {
        return GrpcEndpoint.fromRaw(
            new EndpointId(UUID.fromString(get_id())),
            getTitle(),
            getDescription(),
            getService(),
            isParamStream(),
            isResultStream(),
            SchemaConverter.convertFromJson(getParam()),
            SchemaConverter.convertFromJson(getResult())
        );
    }
}
