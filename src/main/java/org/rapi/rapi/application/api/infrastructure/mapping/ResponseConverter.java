package org.rapi.rapi.application.api.infrastructure.mapping;

import org.rapi.rapi.application.api.endpoint.Response;
import org.rapi.rapi.application.api.infrastructure.dto.ResponseDto;
import org.springframework.stereotype.Service;

@Service
public class ResponseConverter {

    private final HttpStatusCodeConverter httpStatusCodeConverter;
    private final SchemaConverter schemaConverter;

    public ResponseConverter(HttpStatusCodeConverter httpStatusCodeConverter,
        SchemaConverter schemaConverter) {
        this.httpStatusCodeConverter = httpStatusCodeConverter;
        this.schemaConverter = schemaConverter;
    }

    public ResponseDto toResponseDto(Response response) {
        var responseDto = new ResponseDto();
        responseDto.setStatusCode(httpStatusCodeConverter.toInteger(response.statusCode()));
        responseDto.setDescription(response.description());
        responseDto.setSchema(schemaConverter.toSchemaDto(response.schema()));
        return responseDto;
    }

    public Response fromResponseDto(ResponseDto responseDto) {
        return new Response(httpStatusCodeConverter.fromInteger(responseDto.getStatusCode()),
            responseDto.getDescription(), schemaConverter.fromSchemaDto(responseDto.getSchema()));
    }
}
