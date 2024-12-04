package org.rapi.rapi.application.api.endpoint;

import lombok.Getter;
import org.rapi.rapi.application.api.endpoint.route.Route;
import org.rapi.rapi.application.api.structure.schema.Schema;
import org.rapi.rapi.sharedkernel.Entity;

@Getter
public abstract class Endpoint implements Entity<EndpointId> {
    private EndpointId id;
    private String title;
    private String description;
    private Schema request;

    protected Endpoint(EndpointId id, String title, String description, Schema request) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.request = request;
    }
}
