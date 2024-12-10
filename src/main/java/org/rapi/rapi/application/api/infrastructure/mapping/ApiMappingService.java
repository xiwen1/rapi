package org.rapi.rapi.application.api.infrastructure.mapping;

import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.rapi.rapi.application.api.endpoint.GrpcEndpoint;
import org.rapi.rapi.application.api.endpoint.RestfulEndpoint;
import org.rapi.rapi.application.api.group.CrudGroup;
import org.rapi.rapi.application.api.group.JwtGroup;
import org.rapi.rapi.application.api.infrastructure.dto.CrudGroupDto;
import org.rapi.rapi.application.api.infrastructure.dto.GrpcEndpointDto;
import org.rapi.rapi.application.api.infrastructure.dto.InventoryDto;
import org.rapi.rapi.application.api.infrastructure.dto.JwtGroupDto;
import org.rapi.rapi.application.api.infrastructure.dto.RestfulEndpointDto;
import org.rapi.rapi.application.api.infrastructure.dto.StructureDto;
import org.rapi.rapi.application.api.inventory.Inventory;
import org.rapi.rapi.application.api.structure.Structure;
import org.springframework.stereotype.Service;

@Service
public class ApiMappingService {

    private final ModelMapper apiModelMapper;

    public ApiMappingService(ModelMapper apiModelMapper) {
        this.apiModelMapper = apiModelMapper;
    }

    public GrpcEndpointDto toGrpcEndpointDto(GrpcEndpoint grpcEndpoint) {
        return apiModelMapper.map(grpcEndpoint, GrpcEndpointDto.class);
    }

    public GrpcEndpoint fromGrpcEndpointDto(GrpcEndpointDto grpcEndpointDto) {
        return apiModelMapper.map(grpcEndpointDto, GrpcEndpoint.class);
    }

    public RestfulEndpointDto toRestfulEndpointDto(RestfulEndpoint restfulEndpoint) {
        return apiModelMapper.map(restfulEndpoint, RestfulEndpointDto.class);
    }

    public RestfulEndpoint fromRestfulEndpointDto(RestfulEndpointDto restfulEndpointDto) {
        return apiModelMapper.map(restfulEndpointDto, RestfulEndpoint.class);
    }

    public CrudGroupDto toCrudGroupDto(CrudGroup crudGroup) {
        return apiModelMapper.map(crudGroup, CrudGroupDto.class);
    }

    public CrudGroup fromCrudGroupDto(CrudGroupDto crudGroupDto) {
        return apiModelMapper.map(crudGroupDto, CrudGroup.class);
    }

    public JwtGroupDto toJwtGroupDto(JwtGroup jwtGroup) {
        return apiModelMapper.map(jwtGroup, JwtGroupDto.class);
    }

    public JwtGroup fromJwtGroupDto(JwtGroupDto jwtGroupDto) {
        return apiModelMapper.map(jwtGroupDto, JwtGroup.class);
    }

    public StructureDto toStructureDto(Structure structure) {
        return apiModelMapper.map(structure, StructureDto.class);
    }

    public Structure fromStructureDto(StructureDto structureDto) {
        return apiModelMapper.map(structureDto, Structure.class);
    }

    public InventoryDto toInventoryDto(Inventory inventory) {
        return apiModelMapper.map(inventory, InventoryDto.class);
    }

    public Inventory fromInventoryDto(InventoryDto inventoryDto) {
        return apiModelMapper.map(inventoryDto, Inventory.class);
    }

    public UUID fromStringId(String uuid) {
        return apiModelMapper.map(uuid, UUID.class);
    }

    public String toStringId(UUID uuid) {
        return apiModelMapper.map(uuid, String.class);
    }
}
