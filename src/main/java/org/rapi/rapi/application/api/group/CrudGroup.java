package org.rapi.rapi.application.api.group;

import lombok.Getter;
import org.rapi.rapi.application.api.endpoint.Endpoint;
import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.structure.Structure;
import org.rapi.rapi.application.api.structure.StructureId;
import org.rapi.rapi.application.api.structure.schema.NumberSchema;
import org.rapi.rapi.application.api.structure.schema.ObjectSchema;
import org.rapi.rapi.application.api.structure.schema.Schema;
import org.rapi.rapi.application.api.structure.schema.StringSchema;


@Getter
public class CrudGroup extends Group {
    private static final Schema ID_SCHEMA = ObjectSchema.create().addField("id", NumberSchema.create());

    public record CrudEndpoints(Endpoint create, Endpoint read, Endpoint update, Endpoint delete) {}
    public record CrudEndpointIds(EndpointId create, EndpointId read, EndpointId update, EndpointId delete) {}

    private StructureId source;
    private EndpointId createEndpointId;
    private EndpointId readEndpointId;
    private EndpointId updateEndpointId;
    private EndpointId deleteEndpointId;

    private CrudGroup(GroupId id, StructureId source, EndpointId createEndpointId, EndpointId readEndpointId, EndpointId updateEndpointId, EndpointId deleteEndpointId) {
        super(id);
        this.source = source;
        this.createEndpointId = createEndpointId;
        this.readEndpointId = readEndpointId;
        this.updateEndpointId = updateEndpointId;
        this.deleteEndpointId = deleteEndpointId;
    }

    public static CrudGroup create(GroupId id, StructureId source, EndpointId createEndpointId, EndpointId readEndpointId, EndpointId updateEndpointId, EndpointId deleteEndpointId) {
        return new CrudGroup(id, source, createEndpointId, readEndpointId, updateEndpointId, deleteEndpointId);
    }

    public static CrudGroup create(StructureId source, EndpointId createEndpointId, EndpointId readEndpointId, EndpointId updateEndpointId, EndpointId deleteEndpointId) {
        return new CrudGroup(GroupId.create(), source, createEndpointId, readEndpointId, updateEndpointId, deleteEndpointId);
    }

    public static CrudGroup create() {
        return new CrudGroup(GroupId.create(), null, null, null, null, null);
    }

    public CrudEndpoints set(Structure structure) {
        if (this.source != null || this.createEndpointId != null || this.readEndpointId != null || this.updateEndpointId != null || this.deleteEndpointId != null) {
            throw new IllegalStateException("source and endpoints must be null");
        }
        Endpoint create = createCreateEndpoint();
        Endpoint read = createReadEndpoint();
        Endpoint update = createUpdateEndpoint();
        Endpoint delete = createDeleteEndpoint();
        this.source = structure.getId();
        this.createEndpointId = create.getId();
        this.readEndpointId = read.getId();
        this.updateEndpointId = update.getId();
        this.deleteEndpointId = delete.getId();
        return new CrudEndpoints(create, read, update, delete);
    }

    public CrudEndpointIds reset() {
        if (this.source == null || this.createEndpointId == null || this.readEndpointId == null || this.updateEndpointId == null || this.deleteEndpointId == null) {
            throw new IllegalStateException("source and endpoints must not be null");
        }
        var deletedEndpoints = new CrudEndpointIds(this.createEndpointId, this.readEndpointId, this.updateEndpointId, this.deleteEndpointId);
        this.source = null;
        this.createEndpointId = null;
        this.readEndpointId = null;
        this.updateEndpointId = null;
        this.deleteEndpointId = null;
        return deletedEndpoints;
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
}
