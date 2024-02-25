package com.lrm.commentms.dto;

import com.lrm.commentms.external.Blog;
//import com.lrm.commentms.external.User;
import com.lrm.commentms.po.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {
    private Comment comment;
    private Blog blog;
}
