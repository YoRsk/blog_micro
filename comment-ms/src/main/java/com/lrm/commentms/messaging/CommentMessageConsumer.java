package com.lrm.commentms.messaging;

import com.lrm.commentms.dto.CommentMessage;
import com.lrm.commentms.po.Comment;
import com.lrm.commentms.service.CommentService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service//not sure
public class CommentMessageConsumer {
    private final CommentService commentService;

    public CommentMessageConsumer(CommentService commentService) {
        this.commentService = commentService;
    }

    @RabbitListener(queues = "commentQueue")
    public void processMessage(CommentMessage commentMessage) {
        // fill comment object
        Comment comment = new Comment();
        comment.setNickname(commentMessage.getNickname());
        comment.setEmail(commentMessage.getEmail());
        comment.setContent(commentMessage.getContent());
        comment.setAvatar(commentMessage.getAvatar());
        comment.setUserId(commentMessage.getUserId());
        comment.setCreateTime(commentMessage.getCreateTime());
        comment.setBlogId(commentMessage.getBlogId());
        if (commentMessage.getParentCommentId() != null && commentMessage.getParentCommentId() != -1) {
            Optional<Comment> parentComment = commentService.findById(commentMessage.getParentCommentId());
            parentComment.ifPresent(comment::setParentComment);
        }
        commentService.saveComment(comment);
    }
}
