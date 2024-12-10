package org.rapi.rapi.application.discussion.infrastructure.dto;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("discussion")
public class DiscussionDto {

    @Id
    private String id;
    private List<ConversationDto> conversations;
}
