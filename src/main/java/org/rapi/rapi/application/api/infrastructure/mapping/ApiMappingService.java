package org.rapi.rapi.application.api.infrastructure.mapping;

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

    public CrudGroupDto toCrudGroupDto(CrudGroupDto crudGroupDto) {
        return apiModelMapper.map(crudGroupDto, CrudGroupDto.class);
    }

    public CrudGroup fromCrudGroupDto(CrudGroupDto crudGroupDto) {
        return apiModelMapper.map(crudGroupDto, CrudGroup.class);
    }

    public JwtGroupDto toJwtGroupDto(JwtGroupDto jwtGroupDto) {
        return apiModelMapper.map(jwtGroupDto, JwtGroupDto.class);
    }

    public JwtGroup fromJwtGroupDto(JwtGroupDto jwtGroupDto) {
        return apiModelMapper.map(jwtGroupDto, JwtGroup.class);
    }

    public StructureDto toStructureDto(StructureDto structureDto) {
        return apiModelMapper.map(structureDto, StructureDto.class);
    }

    public Structure fromStructureDto(StructureDto structureDto) {
        return apiModelMapper.map(structureDto, Structure.class);
    }

    public InventoryDto toInventoryDto(InventoryDto inventoryDto) {
        return apiModelMapper.map(inventoryDto, InventoryDto.class);
    }

    public Inventory fromInventoryDto(InventoryDto inventoryDto) {
        return apiModelMapper.map(inventoryDto, Inventory.class);
    }
}
