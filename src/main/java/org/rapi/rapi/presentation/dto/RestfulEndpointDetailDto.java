package org.rapi.rapi.presentation.dto;

import java.util.List;
import lombok.Data;

@Data
public class RestfulEndpointDetailDto {

    private String id;
    private String name;
    private String httpMethod;
    private List<RoutePath> routePath;
    private SchemaDto request;
    private List<Response> response;
    private String description;
    private String state;
    private SchemaDto headers;
    private SchemaDto query;


    public RestfulEndpointDetailDto(String id, String name, String httpMethod,
        List<RoutePath> routePath, SchemaDto request, List<Response> response, String description,
        String state, SchemaDto headers, SchemaDto query) {
        this.id = id;
        this.name = name;
        this.httpMethod = httpMethod;
        this.routePath = routePath;
        this.request = request;
        this.response = response;
        this.description = description;
        this.state = state;
        this.headers = headers;
        this.query = query;
    }

    @Data
    public static class Response {

        private int statusCode;
        private String description;
        private SchemaDto schema;

        public Response(int statusCode, String description, SchemaDto schema) {
            this.statusCode = statusCode;
            this.description = description;
            this.schema = schema;
        }
    }

    @Data
    public static class RoutePath {

        private Constant constant;
        private NamedSchema namedSchema;


        @Data
        public static class Constant {

            private String constant;
        }

        @Data
        public static class NamedSchema {

            private String name;
            private SchemaDto schema;
        }
    }
}