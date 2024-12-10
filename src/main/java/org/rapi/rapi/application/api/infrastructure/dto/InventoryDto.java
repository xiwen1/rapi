package org.rapi.rapi.application.api.infrastructure.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryDto {

    private String id;
    private List<String> structures;
    private List<String> jwtGroups;
    private List<String> crudGroups;
    private List<String> restfulEndpoints;
    private List<String> grpcEndpoints;
}
