package org.rapi.rapi.application.api.endpoint.route;

import org.rapi.rapi.application.api.structure.schema.Schema;
import org.springframework.lang.NonNull;

public record SchemaFragment(Schema schema) implements Fragment {
}
