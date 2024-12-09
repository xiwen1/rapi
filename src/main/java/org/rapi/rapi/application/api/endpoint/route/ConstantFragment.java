package org.rapi.rapi.application.api.endpoint.route;

public record ConstantFragment(String constant) implements Fragment {

    public static ConstantFragment create(String constant) {
        return new ConstantFragment(constant);
    }
}
