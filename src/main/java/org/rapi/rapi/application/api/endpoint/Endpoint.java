package org.rapi.rapi.application.api.endpoint;

import lombok.Getter;
import org.rapi.rapi.sharedkernel.Entity;

@Getter
public abstract class Endpoint implements Entity<EndpointId> {

    protected EndpointId id;
    protected String title;
    protected String description;

    protected Endpoint(EndpointId id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
}
