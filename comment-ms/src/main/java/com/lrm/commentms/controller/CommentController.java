package com.lrm.commentms.controller;
import com.lrm.commentms.messaging.CommentMessageProducer;
import com.lrm.commentms.po.Comment;
import com.lrm.commentms.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class
CommentController {
    // http status pushed in CONTROLLER is better
    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentMessageProducer commentMessageProducer;

    @Value("${comment.touristAvatar}")
    private String touristAvatar;

    @Value("${comment.userAvatar}")
    private String userAvatar;

    @GetMapping
    public ResponseEntity<List<Comment>> comments(@RequestParam Long blogId) {
        return new ResponseEntity<>(commentService.listCommentByBlogId(blogId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> post(@RequestBody Comment comment, @RequestParam Long blogId) {
        try{
            //User user = (User) session.getAttribute("user");
            //TODO: get user
            // 模拟用户信息获取，实际项目中应从安全上下文或会话中获取
            Long userId = 1L; // 示例用户ID
            String avatar = userId != null ? userAvatar : touristAvatar;

            comment.setUserId(userId);
            comment.setAvatar(avatar);
            comment.setBlogId(blogId);

            commentMessageProducer.sendCommentMessage(comment);
            return new ResponseEntity<>("Comment is being processed", HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>("Comment Added Failed", HttpStatus.NOT_FOUND);
        }

    }
//        boolean isCommentSaved = commentService.saveComment(blogId, comment);
//        if(isCommentSaved){
//            return new ResponseEntity<>("Comment Added Successfully", HttpStatus.OK);
//        }else{
//            return new ResponseEntity<>("Comment Added Failed", HttpStatus.NOT_FOUND);
//        }
//    }

//    @GetMapping("/{blogId}")
//    public String comments(@PathVariable Long blogId, Model model) {
//        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
//        return "blog :: commentList";
//    }


//    @PostMapping("/comments")
//    public String post(Comment comment, HttpSession session) {//上传评论时
//        Long blogId = comment.getBlog().getId();
//        comment.setBlog(blogService.getBlog(blogId));
//        User user = (User) session.getAttribute("user");
//        if (user != null) {
//            Long userId = user.getId();
//            comment.setAvatar(userAvatar);
//            comment.setUserId(userId);
//            if(user.getId().equals(comment.getBlog().getUser().getId()))
//                comment.setAdminComment(true);
//            else comment.setAdminComment(false);
//        } else {
//            comment.setAvatar(touristAvatar);
//        }
//        commentService.saveComment(comment);
//        return "redirect:/comments/" + blogId;
//    }



}
