package org.rapi.rapi.application.api.infrastructure.mapping;

import java.util.UUID;
import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.endpoint.GrpcEndpoint;
import org.rapi.rapi.application.api.endpoint.RestfulEndpoint;
import org.rapi.rapi.application.api.group.CrudGroup;
import org.rapi.rapi.application.api.group.GroupId;
import org.rapi.rapi.application.api.group.JwtGroup;
import org.rapi.rapi.application.api.infrastructure.dto.CrudGroupDto;
import org.rapi.rapi.application.api.infrastructure.dto.GrpcEndpointDto;
import org.rapi.rapi.application.api.infrastructure.dto.InventoryDto;
import org.rapi.rapi.application.api.infrastructure.dto.JwtGroupDto;
import org.rapi.rapi.application.api.infrastructure.dto.RestfulEndpointDto;
import org.rapi.rapi.application.api.infrastructure.dto.StructureDto;
import org.rapi.rapi.application.api.inventory.Inventory;
import org.rapi.rapi.application.api.inventory.InventoryId;
import org.rapi.rapi.application.api.structure.Structure;
import org.rapi.rapi.application.api.structure.StructureId;
import org.rapi.rapi.application.api.structure.schema.ObjectSchema;
import org.springframework.stereotype.Service;

@Service
public class ApiMappingService {

    private final UuidConverter uuidConverter;
    private final SchemaConverter schemaConverter;
    private final HttpMethodConverter httpMethodConverter;
    private final VavrListConverter vavrListConverter;
    private final RouteConverter routeConverter;
    private final ResponseConverter responseConverter;
    private final VavrOptionNullConverter vavrOptionNullConverter;
    private final VavrMapConverter vavrMapConverter;

    public ApiMappingService(UuidConverter uuidConverter, SchemaConverter schemaConverter,
        HttpMethodConverter httpMethodConverter, VavrListConverter vavrListConverter,
        RouteConverter routeConverter, ResponseConverter responseConverter,
        VavrOptionNullConverter vavrOptionNullConverter, VavrMapConverter vavrMapConverter) {
        this.uuidConverter = uuidConverter;
        this.schemaConverter = schemaConverter;
        this.httpMethodConverter = httpMethodConverter;
        this.vavrListConverter = vavrListConverter;
        this.routeConverter = routeConverter;
        this.responseConverter = responseConverter;
        this.vavrOptionNullConverter = vavrOptionNullConverter;
        this.vavrMapConverter = vavrMapConverter;
    }

    public GrpcEndpointDto toGrpcEndpointDto(GrpcEndpoint grpcEndpoint) {
        var grpcEndpointDto = new GrpcEndpointDto();
        grpcEndpointDto.setId(uuidConverter.toString(grpcEndpoint.getId().id()));
        grpcEndpointDto.setTitle(grpcEndpoint.getTitle());
        grpcEndpointDto.setDescription(grpcEndpoint.getDescription());
        grpcEndpointDto.setService(grpcEndpoint.getService());
        grpcEndpointDto.setParam(schemaConverter.toSchemaDto(grpcEndpoint.getParam()));
        grpcEndpointDto.setResult(schemaConverter.toSchemaDto(grpcEndpoint.getResult()));
        grpcEndpointDto.setParamStream(grpcEndpoint.isParamStream());
        grpcEndpointDto.setResultStream(grpcEndpoint.isResultStream());
        return grpcEndpointDto;
    }

    public GrpcEndpoint fromGrpcEndpointDto(GrpcEndpointDto grpcEndpointDto) {
        return GrpcEndpoint.fromRaw(
            new EndpointId(uuidConverter.fromString(grpcEndpointDto.getId())),
            grpcEndpointDto.getTitle(), grpcEndpointDto.getDescription(),
            grpcEndpointDto.getService(), grpcEndpointDto.isParamStream(),
            grpcEndpointDto.isResultStream(),
            schemaConverter.fromSchemaDto(grpcEndpointDto.getParam()),
            schemaConverter.fromSchemaDto(grpcEndpointDto.getResult()));
    }

    public RestfulEndpointDto toRestfulEndpointDto(RestfulEndpoint restfulEndpoint) {
        var restfulEndpointDto = new RestfulEndpointDto();
        restfulEndpointDto.setId(uuidConverter.toString(restfulEndpoint.getId().id()));
        restfulEndpointDto.setTitle(restfulEndpoint.getTitle());
        restfulEndpointDto.setDescription(restfulEndpoint.getDescription());
        restfulEndpointDto.setMethod(httpMethodConverter.toString(restfulEndpoint.getMethod()));
        restfulEndpointDto.setRoute(routeConverter.toRouteDto(restfulEndpoint.getRoute()));
        restfulEndpointDto.setResponses(vavrListConverter.toList(
            restfulEndpoint.getResponses().map(responseConverter::toResponseDto)));
        restfulEndpointDto.setRequest(vavrOptionNullConverter.toNullable(
            restfulEndpoint.getRequest().map(schemaConverter::toSchemaDto)));
        restfulEndpointDto.setHeader(vavrOptionNullConverter.toNullable(
            restfulEndpoint.getHeader().map(schemaConverter::toSchemaDto)));
        restfulEndpointDto.setQuery(vavrOptionNullConverter.toNullable(
            restfulEndpoint.getQuery().map(schemaConverter::toSchemaDto)));
        return restfulEndpointDto;
    }

    public RestfulEndpoint fromRestfulEndpointDto(RestfulEndpointDto restfulEndpointDto) {
        return RestfulEndpoint.fromRaw(
            new EndpointId(uuidConverter.fromString(restfulEndpointDto.getId())),
            restfulEndpointDto.getTitle(), restfulEndpointDto.getDescription(),
            vavrOptionNullConverter.fromNullable(restfulEndpointDto.getRequest())
                .map(schemaConverter::fromSchemaDto),
            vavrOptionNullConverter.fromNullable(restfulEndpointDto.getHeader())
                .map(schemaConverter::fromSchemaDto).map(ObjectSchema.class::cast),
            vavrOptionNullConverter.fromNullable(restfulEndpointDto.getQuery())
                .map(schemaConverter::fromSchemaDto).map(ObjectSchema.class::cast),
            httpMethodConverter.fromString(restfulEndpointDto.getMethod()),
            routeConverter.fromRouteDto(restfulEndpointDto.getRoute()),
            vavrListConverter.fromList(restfulEndpointDto.getResponses())
                .map(responseConverter::fromResponseDto));
    }

    public CrudGroupDto toCrudGroupDto(CrudGroup crudGroup) {
        var crudGroupDto = new CrudGroupDto();
        crudGroupDto.setId(uuidConverter.toString(crudGroup.getId().id()));
        crudGroupDto.setSource(vavrOptionNullConverter.toNullable(
            crudGroup.getSource().map(structureId -> uuidConverter.toString(structureId.id()))));
        crudGroupDto.setCreateEndpointId(vavrOptionNullConverter.toNullable(
            crudGroup.getCreateEndpointId()
                .map(endpoint -> uuidConverter.toString(endpoint.id()))));
        crudGroupDto.setListEndpointId(vavrOptionNullConverter.toNullable(
            crudGroup.getListEndpointId().map(endpoint -> uuidConverter.toString(endpoint.id()))));
        crudGroupDto.setUpdateEndpointId(vavrOptionNullConverter.toNullable(
            crudGroup.getUpdateEndpointId()
                .map(endpoint -> uuidConverter.toString(endpoint.id()))));
        crudGroupDto.setDeleteEndpointId(vavrOptionNullConverter.toNullable(
            crudGroup.getDeleteEndpointId()
                .map(endpoint -> uuidConverter.toString(endpoint.id()))));
        return crudGroupDto;
    }

    public CrudGroup fromCrudGroupDto(CrudGroupDto crudGroupDto) {
        return CrudGroup.fromRaw(new GroupId(uuidConverter.fromString(crudGroupDto.getId())),
            vavrOptionNullConverter.fromNullable(crudGroupDto.getSource())
                .map(structureId -> new StructureId(uuidConverter.fromString(structureId))),
            vavrOptionNullConverter.fromNullable(crudGroupDto.getCreateEndpointId())
                .map(endpointId -> new EndpointId(uuidConverter.fromString(endpointId))),
            vavrOptionNullConverter.fromNullable(crudGroupDto.getListEndpointId())
                .map(endpointId -> new EndpointId(uuidConverter.fromString(endpointId))),
            vavrOptionNullConverter.fromNullable(crudGroupDto.getUpdateEndpointId())
                .map(endpointId -> new EndpointId(uuidConverter.fromString(endpointId))),
            vavrOptionNullConverter.fromNullable(crudGroupDto.getDeleteEndpointId())
                .map(endpointId -> new EndpointId(uuidConverter.fromString(endpointId))));
    }

    public JwtGroupDto toJwtGroupDto(JwtGroup jwtGroup) {
        var jwtGroupDto = new JwtGroupDto();
        jwtGroupDto.setId(uuidConverter.toString(jwtGroup.getId().id()));
        jwtGroupDto.setLoginEndpointId(uuidConverter.toString(jwtGroup.getLoginEndpointId().id()));
        jwtGroupDto.setRefreshEndpointId(
            uuidConverter.toString(jwtGroup.getRefreshEndpointId().id()));
        jwtGroupDto.setProtectedEndpointsMap(vavrMapConverter.toMap(
            jwtGroup.getProtectedEndpointsMap()
                .mapValues(endpointId -> uuidConverter.toString(endpointId.id()))
                .mapKeys(endpointId -> uuidConverter.toString(endpointId.id()))));
        return jwtGroupDto;
    }

    public JwtGroup fromJwtGroupDto(JwtGroupDto jwtGroupDto) {
        return JwtGroup.fromRaw(new GroupId(uuidConverter.fromString(jwtGroupDto.getId())),
            vavrMapConverter.fromMap(jwtGroupDto.getProtectedEndpointsMap())
                .mapValues(endpointId -> new EndpointId(uuidConverter.fromString(endpointId)))
                .mapKeys(endpointId -> new EndpointId(uuidConverter.fromString(endpointId))),
            new EndpointId(uuidConverter.fromString(jwtGroupDto.getLoginEndpointId())),
            new EndpointId(uuidConverter.fromString(jwtGroupDto.getRefreshEndpointId())));
    }

    public StructureDto toStructureDto(Structure structure) {
        var structureDto = new StructureDto();
        structureDto.setId(uuidConverter.toString(structure.getId().id()));
        structureDto.setSchema(schemaConverter.toSchemaDto(structure.getSchema()));
        structureDto.setName(structure.getName());
        return structureDto;
    }

    public Structure fromStructureDto(StructureDto structureDto) {
        return Structure.fromRaw(new StructureId(uuidConverter.fromString(structureDto.getId())),
            schemaConverter.fromSchemaDto(structureDto.getSchema()), structureDto.getName());
    }

    public InventoryDto toInventoryDto(Inventory inventory) {
        var inventoryDto = new InventoryDto();
        inventoryDto.setId(uuidConverter.toString(inventory.getId().id()));
        inventoryDto.setStructures(vavrListConverter.toList(inventory.getStructures()
            .map(structureId -> uuidConverter.toString(structureId.id()))));
        inventoryDto.setJwtGroups(vavrListConverter.toList(
            inventory.getJwtGroups().map(groupId -> uuidConverter.toString(groupId.id()))));
        inventoryDto.setCrudGroups(vavrListConverter.toList(
            inventory.getCrudGroups().map(groupId -> uuidConverter.toString(groupId.id()))));
        inventoryDto.setRestfulEndpoints(vavrListConverter.toList(inventory.getRestfulEndpoints()
            .map(endpointId -> uuidConverter.toString(endpointId.id()))));
        inventoryDto.setGrpcEndpoints(vavrListConverter.toList(inventory.getGrpcEndpoints()
            .map(endpointId -> uuidConverter.toString(endpointId.id()))));
        return inventoryDto;
    }

    public Inventory fromInventoryDto(InventoryDto inventoryDto) {
        return Inventory.fromRaw(new InventoryId(uuidConverter.fromString(inventoryDto.getId())),
            vavrListConverter.fromList(inventoryDto.getStructures())
                .map(structureId -> new StructureId(uuidConverter.fromString(structureId))),
            vavrListConverter.fromList(inventoryDto.getJwtGroups())
                .map(groupId -> new GroupId(uuidConverter.fromString(groupId))),
            vavrListConverter.fromList(inventoryDto.getCrudGroups())
                .map(groupId -> new GroupId(uuidConverter.fromString(groupId))),
            vavrListConverter.fromList(inventoryDto.getRestfulEndpoints())
                .map(endpointId -> new EndpointId(uuidConverter.fromString(endpointId))),
            vavrListConverter.fromList(inventoryDto.getGrpcEndpoints())
                .map(endpointId -> new EndpointId(uuidConverter.fromString(endpointId))));
    }

    public UUID fromStringId(String uuid) {
        return uuidConverter.fromString(uuid);
    }

    public String toStringId(UUID uuid) {
        return uuidConverter.toString(uuid);
    }
}
