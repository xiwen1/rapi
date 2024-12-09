package org.rapi.rapi.application.api.endpoint.route;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@TypeAlias("constantFragment")
public record ConstantFragment(String constant) implements Fragment {

    public static ConstantFragment create(String constant) {
        return new ConstantFragment(constant);
    }
}
