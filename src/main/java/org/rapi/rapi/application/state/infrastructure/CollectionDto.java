package org.rapi.rapi.application.state.infrastructure;


import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("collection")
public class CollectionDto {

    @Id
    private String id;
    private List<SubjectDto> subjects;
    private List<StateDto> states;
    private String defaultState;
}
