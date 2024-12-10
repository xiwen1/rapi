package org.rapi.rapi.application.discussion.infrastructure.mapping;

import io.vavr.collection.List;
import java.util.UUID;
import org.rapi.rapi.application.discussion.author.Author;
import org.rapi.rapi.application.discussion.author.AuthorId;
import org.rapi.rapi.application.discussion.discussion.Comment;
import org.rapi.rapi.application.discussion.discussion.CommentId;
import org.rapi.rapi.application.discussion.discussion.Conversation;
import org.rapi.rapi.application.discussion.discussion.ConversationId;
import org.rapi.rapi.application.discussion.discussion.Discussion;
import org.rapi.rapi.application.discussion.discussion.DiscussionId;
import org.rapi.rapi.application.discussion.infrastructure.dto.AuthorDto;
import org.rapi.rapi.application.discussion.infrastructure.dto.CommentDto;
import org.rapi.rapi.application.discussion.infrastructure.dto.ConversationDto;
import org.rapi.rapi.application.discussion.infrastructure.dto.DiscussionDto;
import org.springframework.stereotype.Service;

@Service
public class DiscussionMappingService {

    public CommentDto toCommentDto(Comment comment) {
        var dto = new CommentDto();
        dto.setId(comment.getId().id().toString());
        dto.setAuthorId(comment.getAuthorId().id().toString());
        dto.setContent(comment.getContent());
        return dto;
    }

    public Comment fromCommentDto(CommentDto commentDto) {
        return Comment.fromRaw(new CommentId(UUID.fromString(commentDto.getId())),
            commentDto.getContent(), new AuthorId(UUID.fromString(commentDto.getAuthorId())));
    }

    public ConversationDto toConversationDto(Conversation conversation) {
        var dto = new ConversationDto();
        dto.setId(conversation.getId().id().toString());
        dto.setComments(conversation.getComments().map(this::toCommentDto).toJavaList());
        dto.setTitle(conversation.getTitle());
        dto.setClosed(conversation.isClosed());
        dto.setStarter(conversation.getStarter().id().toString());
        return dto;
    }

    public Conversation fromConversationDto(ConversationDto conversationDto) {
        var comments = conversationDto.getComments().stream().map(this::fromCommentDto).toList();
        return Conversation.fromRaw(new ConversationId(UUID.fromString(conversationDto.getId())),
            conversationDto.getTitle(), List.ofAll(comments), conversationDto.isClosed(),
            new AuthorId(UUID.fromString(conversationDto.getStarter())));
    }

    public AuthorDto toAuthorDto(Author author) {
        var dto = new AuthorDto();
        dto.setId(author.getId().id().toString());
        return dto;
    }

    public Author fromAuthorDto(AuthorDto authorDto) {
        return Author.fromRaw(new AuthorId(UUID.fromString(authorDto.getId())));
    }

    public DiscussionDto toDiscussionDto(Discussion discussion) {
        var dto = new DiscussionDto();
        dto.setId(discussion.getId().id().toString());
        dto.setConversations(
            discussion.getConversations().map(this::toConversationDto).toJavaList());
        return dto;
    }

    public Discussion fromDiscussionDto(DiscussionDto discussionDto) {
        var conversations = discussionDto.getConversations().stream().map(this::fromConversationDto)
            .toList();
        return Discussion.fromRaw(new DiscussionId(UUID.fromString(discussionDto.getId())),
            List.ofAll(conversations));
    }


}
