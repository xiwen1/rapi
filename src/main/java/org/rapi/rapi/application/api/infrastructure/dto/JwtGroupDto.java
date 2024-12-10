package org.rapi.rapi.application.api.infrastructure.dto;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtGroupDto {

    private String id;
    private String loginEndpointId;
    private String refreshEndpointId;
    private Map<String, String> protectedEndpointsMap;
}
