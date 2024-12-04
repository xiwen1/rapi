package org.rapi.rapi.application.api.endpoint.route;

import lombok.Getter;

public record ConstantFragment(String constant) implements Fragment {
    public ConstantFragment {
        if (constant == null) {
            throw new IllegalArgumentException("Constant cannot be null");
        }
    }
}
