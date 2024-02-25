package com.lrm.commentms.messaging;

import com.lrm.commentms.dto.CommentMessage;
import com.lrm.commentms.po.Comment;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CommentMessageProducer {
    private final RabbitTemplate rabbitTemplate;
    public CommentMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendCommentMessage(Comment comment){
        CommentMessage commentMessage = new CommentMessage();
        BeanUtils.copyProperties(comment, commentMessage);
        rabbitTemplate.convertAndSend("commentQueue", commentMessage);
    }
    public void sendCommentMessageToBlog(Comment comment) {
        // define a special CommentMessage object
        CommentMessage commentMessage = new CommentMessage();
        commentMessage.setId(comment.getId());
        commentMessage.setContent(commentMessage.getContent());
        commentMessage.setBlogId(comment.getBlogId());
        //if parentComment is null, set parentCommentId
        if (comment.getParentComment() != null) {
            commentMessage.setParentCommentId(comment.getParentComment().getId());
        }

        rabbitTemplate.convertAndSend("commentSpecializedQueue", commentMessage);
    }
}
