package org.rapi.rapi.application.api.infrastructure.mapping;

import org.springframework.stereotype.Service;

@Service
public class HttpMethodConverter {

    public String toString(org.springframework.http.HttpMethod httpMethod) {
        return httpMethod.name();
    }

    public org.springframework.http.HttpMethod fromString(String string) {
        return org.springframework.http.HttpMethod.valueOf(string);
    }

}
