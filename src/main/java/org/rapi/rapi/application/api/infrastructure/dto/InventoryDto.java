package org.rapi.rapi.application.api.infrastructure.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("inventory")
public class InventoryDto {

    @Id
    private String id;
    private List<String> structures;
    private List<String> jwtGroups;
    private List<String> crudGroups;
    private List<String> restfulEndpoints;
    private List<String> grpcEndpoints;
}
