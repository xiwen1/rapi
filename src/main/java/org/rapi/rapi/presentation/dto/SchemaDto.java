package org.rapi.rapi.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SchemaDto {

    private String type;
    private SchemaDto item;
    private List<KeyValuePair> fields;
    private String ref;

    @Data
    public static class KeyValuePair {

        private String key;
        private SchemaDto value;
    }
}
