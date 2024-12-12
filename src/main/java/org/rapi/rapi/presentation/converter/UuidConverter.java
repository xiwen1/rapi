package org.rapi.rapi.presentation.converter;

import org.springframework.stereotype.Service;

@Service
public class UuidConverter {

    public String toString(java.util.UUID uuid) {
        return uuid.toString();
    }

    public java.util.UUID fromString(String string) {
        return java.util.UUID.fromString(string);
    }

}
