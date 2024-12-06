package org.rapi.rapi.application.api.group;

import io.vavr.collection.List;
import io.vavr.control.Option;
import lombok.Getter;
import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.endpoint.Response;
import org.rapi.rapi.application.api.endpoint.RestfulEndpoint;
import org.rapi.rapi.application.api.endpoint.route.ConstantFragment;
import org.rapi.rapi.application.api.endpoint.route.Route;
import org.rapi.rapi.application.api.endpoint.route.SchemaFragment;
import org.rapi.rapi.application.api.structure.Structure;
import org.rapi.rapi.application.api.structure.StructureId;
import org.rapi.rapi.application.api.structure.schema.ListSchema;
import org.rapi.rapi.application.api.structure.schema.NumberSchema;
import org.rapi.rapi.application.api.structure.schema.ObjectSchema;
import org.rapi.rapi.application.api.structure.schema.Schema;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;

import java.util.Arrays;

@Getter
public class CrudGroup extends Group {

    private static final Schema ID_SCHEMA = NumberSchema.create();
    private static final String ID_NAME = "id";
    private static final SchemaFragment ID_ROUTE_FRAGMENT = SchemaFragment.create(ID_NAME,
            ID_SCHEMA);
    private Option<StructureId> source;
    private Option<EndpointId> createEndpointId;
    private Option<EndpointId> listEndpointId;
    private Option<EndpointId> updateEndpointId;
    private Option<EndpointId> deleteEndpointId;

    private CrudGroup(GroupId id, Option<StructureId> source, Option<EndpointId> createEndpointId,
                      Option<EndpointId> listEndpointId, Option<EndpointId> updateEndpointId,
                      Option<EndpointId> deleteEndpointId) {
        super(id);
        this.source = source;
        this.createEndpointId = createEndpointId;
        this.listEndpointId = listEndpointId;
        this.updateEndpointId = updateEndpointId;
        this.deleteEndpointId = deleteEndpointId;
    }

    /**
     * Create a new CrudGroup
     *
     * @return The new CrudGroup
     * @apiNote The id is generated automatically. The source, createEndpointId, listEndpointId,
     * updateEndpointId, deleteEndpointId are empty. The group is in unset state.
     */
    public static CrudGroup create() {
        return new CrudGroup(GroupId.create(), Option.none(), Option.none(), Option.none(),
                Option.none(),
                Option.none());
    }

    /**
     * Set the source and create the endpoints
     *
     * @param structure The structure to set as source
     * @return The created endpoints
     * @apiNote Can only be set when the group is in unset state.
     */
    public CrudEndpoints set(Structure structure) {
        if (source.isDefined() || createEndpointId.isDefined() || listEndpointId.isDefined() ||
                updateEndpointId.isDefined() || deleteEndpointId.isDefined()) {
            throw new IllegalStateException(
                    "Cannot set source and endpoints when they are already set");
        }

        CrudEndpoints crudEndpoints = new CrudEndpoints(
                createCreateEndpoint(structure),
                createListEndpoint(structure),
                createUpdateEndpoint(structure),
                createDeleteEndpoint(structure)
        );

        source = Option.some(structure.getId());
        createEndpointId = Option.some(crudEndpoints.create.getId());
        listEndpointId = Option.some(crudEndpoints.list.getId());
        updateEndpointId = Option.some(crudEndpoints.update.getId());
        deleteEndpointId = Option.some(crudEndpoints.delete.getId());

        return crudEndpoints;
    }

    /**
     * Reset the source and endpoints
     *
     * @return The reset endpoints.
     * @apiNote Can only be reset when the group is in set state.
     */
    public CrudEndpointIds reset() {
        if (source.isEmpty() || createEndpointId.isEmpty() || listEndpointId.isEmpty()
                || updateEndpointId.isEmpty() ||
                deleteEndpointId.isEmpty()) {
            throw new IllegalStateException(
                    "Cannot reset source and endpoints when they are not set");
        }

        CrudEndpointIds crudEndpointIds = new CrudEndpointIds(
                createEndpointId.get(),
                listEndpointId.get(),
                updateEndpointId.get(),
                deleteEndpointId.get()
        );

        source = Option.none();
        createEndpointId = Option.none();
        listEndpointId = Option.none();
        updateEndpointId = Option.none();
        deleteEndpointId = Option.none();

        return crudEndpointIds;
    }

    /**
     * Regenerate the endpoints
     *
     * @param structure The structure to regenerate the endpoints
     * @return The regenerated endpoints
     * @apiNote Can only be regenerated when the group is in set state. The source must be the same
     * as the structure id. Regeneration won't touch the source and endpoints ids.
     */
    public CrudEndpoints regenerate(Structure structure) {
        if (source.isEmpty() || !source.get().equals(structure.getId())) {
            throw new IllegalStateException(
                    "Cannot regenerate endpoints when the source is not set or the source id is different from the structure id");
        }

        if (createEndpointId.isEmpty() || listEndpointId.isEmpty() || updateEndpointId.isEmpty() ||
                deleteEndpointId.isEmpty()) {
            throw new IllegalStateException("Cannot regenerate endpoints when they are not set");
        }

        RestfulEndpoint newCreateEndpoint = RestfulEndpoint.create(createEndpointId.get(),
                createCreateEndpoint(structure));
        RestfulEndpoint newListEndpoint = RestfulEndpoint.create(listEndpointId.get(),
                createListEndpoint(structure));
        RestfulEndpoint newUpdateEndpoint = RestfulEndpoint.create(updateEndpointId.get(),
                createUpdateEndpoint(structure));
        RestfulEndpoint newDeleteEndpoint = RestfulEndpoint.create(deleteEndpointId.get(),
                createDeleteEndpoint(structure));

        return new CrudEndpoints(newCreateEndpoint, newListEndpoint, newUpdateEndpoint,
                newDeleteEndpoint);
    }

    /**
     * Dissolve the group Can only be dissolved when the group is in set state.
     *
     * @param original The original endpoints
     * @return The endpoints left after dissolution
     * @apiNote Dissolution only generates the endpoints left after dissolution.
     */
    public CrudEndpoints dissolve(CrudEndpoints original) {
        if (source.isEmpty() || createEndpointId.isEmpty() || listEndpointId.isEmpty()
                || updateEndpointId.isEmpty() ||
                deleteEndpointId.isEmpty()) {
            throw new IllegalStateException("Cannot dissolve group when they are not set");
        }

        if (!original.create.getId().equals(createEndpointId.get()) || !original.list.getId()
                .equals(listEndpointId.get()) ||
                !original.update.getId()
                        .equals(updateEndpointId.get()) || !original.delete.getId()
                .equals(deleteEndpointId.get())) {
            throw new IllegalStateException(
                    "Cannot dissolve group when the original endpoints ids are different from the group endpoints ids");
        }

        return new CrudEndpoints(
                original.create,
                original.list,
                original.update,
                original.delete
        );
    }

    @Override
    public List<EndpointId> getGeneratedEndpoints() {
        return List.of(createEndpointId, listEndpointId, updateEndpointId, deleteEndpointId)
                .filter(Option::isDefined)
                .map(Option::get);
    }

    /**
     * Creates a RESTful endpoint for creating a new entity of the given structure.
     *
     * @param structure The structure for which the create endpoint is to be created.
     * @return A RestfulEndpoint configured for creating a new entity.
     */
    private RestfulEndpoint createCreateEndpoint(Structure structure) {
        return RestfulEndpoint.create("Create " + structure.getName(),
                "Create " + structure.getName(),
                structure.getSchema(), HttpMethod.POST,
                Route.create(ConstantFragment.create(snakeCase(structure.getName()))), List.of(
                        Response.create(HttpStatusCode.valueOf(201), "Success", ID_SCHEMA),
                        Response.create(HttpStatusCode.valueOf(400), "Failed", ObjectSchema.create())));
    }

    /**
     * Creates a RESTful endpoint for listing entities of the given structure.
     *
     * @param structure The structure for which the list endpoint is to be created.
     * @return A RestfulEndpoint configured for listing entities.
     */
    private RestfulEndpoint createListEndpoint(Structure structure) {
        return RestfulEndpoint.create("List " + structure.getName(), "List " + structure.getName(),
                HttpMethod.GET,
                Route.create(ConstantFragment.create(snakeCase(structure.getName()))), List.of(
                        Response.create(HttpStatusCode.valueOf(200), "Success",
                                ListSchema.create(structure.getSchema())),
                        Response.create(HttpStatusCode.valueOf(400), "Failed", ObjectSchema.create())));
    }

    /**
     * Creates a RESTful endpoint for updating an existing entity of the given structure.
     *
     * @param structure The structure for which the update endpoint is to be created.
     * @return A RestfulEndpoint configured for updating an existing entity.
     */
    private RestfulEndpoint createUpdateEndpoint(Structure structure) {
        return RestfulEndpoint.create("Update " + structure.getName(),
                "Update " + structure.getName(),
                structure.getSchema(), HttpMethod.PUT,
                Route.create(ConstantFragment.create(snakeCase(structure.getName())),
                        ID_ROUTE_FRAGMENT), List.of(
                        Response.create(HttpStatusCode.valueOf(200), "Success", ID_SCHEMA),
                        Response.create(HttpStatusCode.valueOf(400), "Failed", ObjectSchema.create())));
    }

    /**
     * Creates a RESTful endpoint for deleting an existing entity of the given structure.
     *
     * @param structure The structure for which the delete endpoint is to be created.
     * @return A RestfulEndpoint configured for deleting an existing entity.
     */
    private RestfulEndpoint createDeleteEndpoint(Structure structure) {
        return RestfulEndpoint.create("Delete " + structure.getName(),
                "Delete " + structure.getName(),
                HttpMethod.DELETE,
                Route.create(ConstantFragment.create(snakeCase(structure.getName())),
                        ID_ROUTE_FRAGMENT), List.of(
                        Response.create(HttpStatusCode.valueOf(204), "Success", ID_SCHEMA),
                        Response.create(HttpStatusCode.valueOf(400), "Failed", ObjectSchema.create())));
    }

    private String snakeCase(String name) {
        return List.ofAll(Arrays.stream(name.split("\\s+")))
                .map(String::toLowerCase)
                .fold("", (acc, s) -> acc + "_" + s);

    }

    public record CrudEndpoints(RestfulEndpoint create, RestfulEndpoint list,
                                RestfulEndpoint update,
                                RestfulEndpoint delete) {

    }

    public record CrudEndpointIds(EndpointId create, EndpointId list, EndpointId update,
                                  EndpointId delete) {

    }
}
