package com.example.zhidao.service;

import com.example.zhidao.pojo.entity.Comment;

import java.util.List;

public interface CommentService {
    Comment createComment(String username, Long answerId, String content);

    void deleteComment(String username, Long commentId);

    List<Comment> findCommentPages(Long answerId, Integer page, Integer pageSize);
}
