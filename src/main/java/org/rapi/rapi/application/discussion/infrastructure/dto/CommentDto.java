package org.rapi.rapi.application.discussion.infrastructure.dto;

import lombok.Data;

@Data
public class CommentDto {

    private String id;
    private String authorId;
    private String content;
}
