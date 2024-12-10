package org.rapi.rapi.application.api.infrastructure.dto;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("jwt_group")
public class JwtGroupDto {

    private String id;
    private String loginEndpointId;
    private String refreshEndpointId;
    private Map<String, String> protectedEndpointsMap;
}
