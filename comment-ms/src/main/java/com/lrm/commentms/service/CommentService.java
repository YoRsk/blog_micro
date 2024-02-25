package com.lrm.commentms.service;

import com.lrm.commentms.po.Comment;

import java.util.List;
import java.util.Optional;


public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);
    Optional<Comment> findById(Long id);
}
