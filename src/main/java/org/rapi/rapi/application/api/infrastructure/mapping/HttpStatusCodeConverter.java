package org.rapi.rapi.application.api.infrastructure.mapping;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

@Service
public class HttpStatusCodeConverter {

    public HttpStatusCode fromInteger(int statusCode) {
        return HttpStatusCode.valueOf(statusCode);
    }

    public int toInteger(HttpStatusCode httpStatusCode) {
        return httpStatusCode.value();
    }
}
