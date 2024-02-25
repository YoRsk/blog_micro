package com.lrm.commentms.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentMessage {
    private Long id;
    private String nickname;
    private String email;
    private String content;
    private String avatar;
    private Long userId;
    private Date createTime;
    private Long blogId;
    private Long parentCommentId;
}
