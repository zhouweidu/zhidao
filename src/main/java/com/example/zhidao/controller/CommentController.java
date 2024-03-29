package com.example.zhidao.controller;

import com.example.zhidao.mapper.CommentMapper;
import com.example.zhidao.pojo.entity.Comment;
import com.example.zhidao.pojo.entity.User;
import com.example.zhidao.pojo.vo.comment.CommentVO;
import com.example.zhidao.pojo.vo.comment.CreateCommentRequest;
import com.example.zhidao.pojo.vo.comment.DeleteCommentRequest;
import com.example.zhidao.pojo.vo.comment.FindCommentPagesRequest;
import com.example.zhidao.pojo.vo.common.ResultResponse;
import com.example.zhidao.service.CommentService;
import com.example.zhidao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    @PostMapping("/comment")
    public ResultResponse createComment(@Valid @RequestBody CreateCommentRequest createCommentRequest) {
        Comment comment = commentService.createComment(createCommentRequest.getUsername(),
                createCommentRequest.getAnswerId(), createCommentRequest.getContent());
        return ResultResponse.success(CommentMapper.INSTANCT.entity2VO(comment));
    }

    @DeleteMapping("/comment")
    public ResultResponse deleteComment(@Valid DeleteCommentRequest deleteCommentRequest) {
        commentService.deleteComment(deleteCommentRequest.getUsername(), deleteCommentRequest.getCommentId());
        return ResultResponse.success();
    }

    @GetMapping("/comment")
    public ResultResponse findCommentPages(@Valid FindCommentPagesRequest findCommentRequest) {
        List<Comment> commentPages = commentService.findCommentPages(findCommentRequest.getAnswerId(),
                findCommentRequest.getPage(), findCommentRequest.getPageSize());
        ArrayList<CommentVO> commentVOList = new ArrayList<>();
        for (Comment commentPage : commentPages) {
            User userInfo = userService.findUserInfo(commentPage.getUserId());
            commentVOList.add(CommentMapper.INSTANCT.entity2VO(commentPage, userInfo.getNickName()));
        }
        return ResultResponse.success(commentVOList);
    }

    @PostMapping("/comment/vote/{commentId}")
    public ResultResponse likedComment(@PathVariable("commentId") Long commentId) {
        commentService.likedComment(commentId);
        return ResultResponse.success();
    }

    @DeleteMapping("/comment/vote/{commentId}")
    public ResultResponse unlikedComment(@PathVariable("commentId") Long commentId) {
        commentService.unlikedComment(commentId);
        return ResultResponse.success();
    }
}
