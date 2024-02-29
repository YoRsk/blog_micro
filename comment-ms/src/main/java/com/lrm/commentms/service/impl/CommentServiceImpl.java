package com.lrm.commentms.service.impl;

import com.lrm.commentms.clients.BlogClient;
import com.lrm.commentms.dao.CommentRepository;
import com.lrm.commentms.dto.CommentDTO;
import com.lrm.commentms.external.Blog;
import com.lrm.commentms.po.Comment;
import com.lrm.commentms.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BlogClient blogClient;

    public CommentServiceImpl(CommentRepository commentRepository, BlogClient blogClient) {
        this.blogClient = blogClient;
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = Sort.by("createTime");
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId, sort);
        return eachComment(comments);
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        if (comment.getParentComment() != null && comment.getParentComment().getId() != null) {
            // 如果有父评论ID，则尝试从数据库中查找父评论
            Comment parentComment = commentRepository.findById(comment.getParentComment().getId()).orElse(null);
            comment.setParentComment(parentComment);
        } else {
            // 如果没有提供父评论ID，或者父评论ID为-1，表示这是一个顶级评论
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }


    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    /* Private Function Block ↓ */




    /**
     * CommentWithBlogDTO
     */
    private CommentDTO convertToDTO(Comment comment) {
        Blog blog = blogClient.getBlog(comment.getBlogId());
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setComment(comment);
        commentDTO.setBlog(blog);
/*                restTemplate.getForObject(
                        "http://BLOG-SERVICE:8081/blog/" + comment.getBlogId(), Blog.class));*/
        return commentDTO;
    }
    /**
     * 循环每个顶级的评论节点
     *
     * @param comments comments
     * @return List
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);

        return commentsView;
//                .stream().toList();
    }
    /**
     * @param comments root根节点，blog不为空的对象集合
     * return 评论的各层子代集合
     */
    private void combineChildren(List<Comment> comments) {

        for (Comment comment : comments) {
            List<Comment> replies = comment.getReplyComments();
            for (Comment reply : replies) {
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }
    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();

    /**
     * 递归迭代，剥洋葱
     *
     * @param comment 被迭代的对象
     * return 递归迭代
     */
    private void recursively(Comment comment) {
        tempReplys.add(comment);//顶节点添加到临时存放集合
        if (!comment.getReplyComments().isEmpty()) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                tempReplys.add(reply);
                if (!reply.getReplyComments().isEmpty()) {
                    recursively(reply);
                }
            }
        }
    }
}
