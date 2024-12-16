package org.rapi.rapi.presentation.controller;

import java.util.List;
import org.rapi.rapi.application.DomainIdMappingService;
import org.rapi.rapi.application.api.endpoint.EndpointId;
import org.rapi.rapi.application.api.infrastructure.mapping.UuidConverter;
import org.rapi.rapi.application.auth.service.query.GetUserByIdQuery;
import org.rapi.rapi.application.discussion.discussion.ConversationId;
import org.rapi.rapi.application.discussion.service.command.CloseConversationCommand;
import org.rapi.rapi.application.discussion.service.command.PostCommentCommand;
import org.rapi.rapi.application.discussion.service.command.ReopenConversationCommand;
import org.rapi.rapi.application.discussion.service.command.StartConversationCommand;
import org.rapi.rapi.application.discussion.service.query.GetAllCommentsByConversationIdQuery;
import org.rapi.rapi.application.discussion.service.query.GetAllConversationsQuery;
import org.rapi.rapi.application.state.service.command.SwitchStateCommand;
import org.rapi.rapi.presentation.GetCurrentUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/discussion/{endpoint_id}")
public class DiscussionController {

    private final GetAllConversationsQuery getAllConversationsQuery;
    private final UuidConverter uuidConverter;
    private final DomainIdMappingService domainIdMappingService;
    private final StartConversationCommand startConversationCommand;
    private final GetCurrentUserService getCurrentUserService;
    private final GetAllCommentsByConversationIdQuery getAllCommentsByConversationIdQuery;
    private final GetUserByIdQuery getUserByIdQuery;
    private final PostCommentCommand postCommentCommand;
    private final SwitchStateCommand switchStateCommand;
    private final CloseConversationCommand closeConversationCommand;
    private final ReopenConversationCommand reopenConversationCommand;

    public DiscussionController(GetAllConversationsQuery getAllConversationsQuery,
        UuidConverter uuidConverter, DomainIdMappingService domainIdMappingService,
        StartConversationCommand startConversationCommand,
        GetCurrentUserService getCurrentUserService,
        GetAllCommentsByConversationIdQuery getAllCommentsByConversationIdQuery,
        GetUserByIdQuery getUserByIdQuery, PostCommentCommand postCommentCommand,
        SwitchStateCommand switchStateCommand, CloseConversationCommand closeConversationCommand,
        ReopenConversationCommand reopenConversationCommand) {
        this.getAllConversationsQuery = getAllConversationsQuery;
        this.uuidConverter = uuidConverter;
        this.domainIdMappingService = domainIdMappingService;
        this.startConversationCommand = startConversationCommand;
        this.getCurrentUserService = getCurrentUserService;
        this.getAllCommentsByConversationIdQuery = getAllCommentsByConversationIdQuery;
        this.getUserByIdQuery = getUserByIdQuery;
        this.postCommentCommand = postCommentCommand;
        this.switchStateCommand = switchStateCommand;
        this.closeConversationCommand = closeConversationCommand;
        this.reopenConversationCommand = reopenConversationCommand;
    }

    @GetMapping("/conversation")
    public ResponseEntity<List<ConversationItem>> getAllConversations(
        @PathVariable("endpoint_id") String endpointIdString) {
        var endpointId = new EndpointId(uuidConverter.fromString(endpointIdString));
        var discussionId = domainIdMappingService.getDiscussionId(endpointId);
        var conversations = getAllConversationsQuery.getAllConversations(discussionId);
        return ResponseEntity.ok(conversations.map(
                conversation -> new ConversationItem(uuidConverter.toString(conversation.getId().id()),
                    conversation.getTitle(), !conversation.isClosed()))
            .toJavaList());
    }

    @PostMapping("/conversation")
    public ResponseEntity<CreateConversationResponse> createConversation(
        @PathVariable("endpoint_id") String endpointIdString,
        @RequestBody CreateConversationRequest request) {
        var user = getCurrentUserService.getUser();
        var authorId = domainIdMappingService.getAuthorId(user.getId());
        var endpointId = new EndpointId(uuidConverter.fromString(endpointIdString));
        var discussionId = domainIdMappingService.getDiscussionId(endpointId);
        var conversationId = startConversationCommand.startConversation(discussionId,
            request.title(), authorId);
        return ResponseEntity.ok(
            new CreateConversationResponse(uuidConverter.toString(conversationId.id())));
    }

    @PostMapping("/conversation/{conversation_id}/state")
    public ResponseEntity<Void> changeConversationState(
        @PathVariable("endpoint_id") String endpointIdString,
        @PathVariable("conversation_id") String conversationIdString,
        @RequestBody ChangeConversationStateRequest request) {
        var user = getCurrentUserService.getUser();
        var authorId = domainIdMappingService.getAuthorId(user.getId());
        var endpointId = new EndpointId(uuidConverter.fromString(endpointIdString));
        var discussionId = domainIdMappingService.getDiscussionId(endpointId);
        var conversationId = new ConversationId(uuidConverter.fromString(conversationIdString));
        switch (request.action()) {
            case "close" -> {
                closeConversationCommand.closeConversation(discussionId, conversationId, authorId);
            }
            case "reopen" -> {
                reopenConversationCommand.reopenConversation(discussionId, conversationId,
                    authorId);
            }
            default -> {
                return ResponseEntity.badRequest().build();
            }

        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/conversation/{conversation_id}")
    public ResponseEntity<ConversationDetailResponse> getConversationDetail(
        @PathVariable("endpoint_id") String endpointIdString,
        @PathVariable("conversation_id") String conversationIdString) {
        var endpointId = new EndpointId(uuidConverter.fromString(endpointIdString));
        var discussionId = domainIdMappingService.getDiscussionId(endpointId);
        var conversationId = new ConversationId(uuidConverter.fromString(conversationIdString));
        var comments = getAllCommentsByConversationIdQuery.getAllCommentsByConversationId(
            conversationId, discussionId);
        return ResponseEntity.ok(
            new ConversationDetailResponse(uuidConverter.toString(conversationId.id()),
                comments.map(
                    comment -> {
                        var authorId = comment.getAuthorId();
                        var userId = domainIdMappingService.getUserId(authorId);
                        var user = getUserByIdQuery.getUserById(userId);
                        return new ConversationDetailResponse.CommentItem(
                            uuidConverter.toString(comment.getId().id()),
                            comment.getContent(), user.getUsername());
                    }).toJavaList()));
    }


    @PostMapping("/conversation/{conversation_id}/comment")
    public ResponseEntity<PostCommentResponse> postComment(
        @PathVariable("endpoint_id") String endpointIdString,
        @PathVariable("conversation_id") String conversationIdString,
        @RequestBody PostCommentRequest request) {
        var user = getCurrentUserService.getUser();
        var authorId = domainIdMappingService.getAuthorId(user.getId());
        var endpointId = new EndpointId(uuidConverter.fromString(endpointIdString));
        var discussionId = domainIdMappingService.getDiscussionId(endpointId);
        var conversationId = new ConversationId(uuidConverter.fromString(conversationIdString));
        var commentId = postCommentCommand.postComment(discussionId, conversationId,
            authorId, request.content());
        return ResponseEntity.ok(new PostCommentResponse(uuidConverter.toString(commentId.id())));
    }




    public record ChangeConversationStateRequest(String action) {

    }

    public record PostCommentRequest(String content) {

    }

    public record PostCommentResponse(String id) {

    }

    public record ConversationDetailResponse(String id, List<CommentItem> comments) {

        public record CommentItem(String id, String content, String author) {

        }

    }

    public record CreateConversationRequest(String title) {

    }

    public record CreateConversationResponse(String id) {

    }


    public record ConversationItem(String id, String title, boolean open) {

    }


}
