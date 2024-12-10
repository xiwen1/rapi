package org.rapi.rapi.application.discussion.infrastructure.dto;

import java.util.List;
import lombok.Data;

@Data
public class ConversationDto {

    private String id;
    private String title;
    private List<CommentDto> comments;
    private boolean isClosed;
    private String starter;
}
