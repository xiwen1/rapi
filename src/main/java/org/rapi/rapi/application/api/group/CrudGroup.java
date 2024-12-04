package org.rapi.rapi.application.api.group;

import io.vavr.control.Option;
import lombok.Getter;
import org.rapi.rapi.application.api.endpoint.Endpoint;
import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.structure.Structure;
import org.rapi.rapi.application.api.structure.StructureId;
import org.rapi.rapi.application.api.structure.schema.NumberSchema;
import org.rapi.rapi.application.api.structure.schema.ObjectSchema;
import org.rapi.rapi.application.api.structure.schema.Schema;

@Getter
public class CrudGroup extends Group {
    private static final Schema ID_SCHEMA = ObjectSchema.create().addField("id", NumberSchema.create());
    private Option<StructureId> source;
    private Option<EndpointId> createEndpointId;
    private Option<EndpointId> readEndpointId;
    private Option<EndpointId> updateEndpointId;
    private Option<EndpointId> deleteEndpointId;
    private CrudGroup(GroupId id, Option<StructureId> source, Option<EndpointId> createEndpointId, Option<EndpointId> readEndpointId, Option<EndpointId> updateEndpointId, Option<EndpointId> deleteEndpointId) {
        super(id);
        this.source = source;
        this.createEndpointId = createEndpointId;
        this.readEndpointId = readEndpointId;
        this.updateEndpointId = updateEndpointId;
        this.deleteEndpointId = deleteEndpointId;
    }

    public static CrudGroup create(GroupId id, Option<StructureId> source, Option<EndpointId> createEndpointId, Option<EndpointId> readEndpointId, Option<EndpointId> updateEndpointId, Option<EndpointId> deleteEndpointId) {
        return new CrudGroup(id, source, createEndpointId, readEndpointId, updateEndpointId, deleteEndpointId);
    }

    public static CrudGroup create(Option<StructureId> source, Option<EndpointId> createEndpointId, Option<EndpointId> readEndpointId, Option<EndpointId> updateEndpointId, Option<EndpointId> deleteEndpointId) {
        return new CrudGroup(GroupId.create(), source, createEndpointId, readEndpointId, updateEndpointId, deleteEndpointId);
    }

    public static CrudGroup create() {
        return new CrudGroup(GroupId.create(), Option.none(), Option.none(), Option.none(), Option.none(), Option.none());
    }

    public CrudEndpoints set(Structure structure) {
        if (source.isDefined() || createEndpointId.isDefined() || readEndpointId.isDefined() || updateEndpointId.isDefined() || deleteEndpointId.isDefined()) {
            throw new IllegalStateException("Cannot set source and endpoints when they are already set");
        }
        CrudEndpoints crudEndpoints = new CrudEndpoints(
            createCreateEndpoint(structure),
            createReadEndpoint(structure),
            createUpdateEndpoint(structure),
            createDeleteEndpoint(structure)
        );
        source = Option.some(structure.getId());
        createEndpointId = Option.some(crudEndpoints.create.getId());
        readEndpointId = Option.some(crudEndpoints.read.getId());
        updateEndpointId = Option.some(crudEndpoints.update.getId());
        deleteEndpointId = Option.some(crudEndpoints.delete.getId());
        return crudEndpoints;
    }

    public CrudEndpointIds reset() {
        if (source.isEmpty() || createEndpointId.isEmpty() || readEndpointId.isEmpty() || updateEndpointId.isEmpty() || deleteEndpointId.isEmpty()) {
            throw new IllegalStateException("Cannot reset source and endpoints when they are not set");
        }
        CrudEndpointIds crudEndpointIds = new CrudEndpointIds(
            createEndpointId.get(),
            readEndpointId.get(),
            updateEndpointId.get(),
            deleteEndpointId.get()
        );
        source = Option.none();
        createEndpointId = Option.none();
        readEndpointId = Option.none();
        updateEndpointId = Option.none();
        deleteEndpointId = Option.none();
        return crudEndpointIds;
    }

    public CrudEndpoints regenerate() {

    }

    public CrudEndpoints dissolve() {

    }

    private Endpoint createCreateEndpoint(Structure structure) {

    }

    private Endpoint createReadEndpoint(Structure structure) {
    }

    private Endpoint createUpdateEndpoint(Structure structure) {
    }

    private Endpoint createDeleteEndpoint(Structure structure) {
    }

    public record CrudEndpoints(Endpoint create, Endpoint read, Endpoint update, Endpoint delete) {
    }

    public record CrudEndpointIds(EndpointId create, EndpointId read, EndpointId update, EndpointId delete) {
    }
}
