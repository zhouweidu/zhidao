package com.example.zhidao.service;

import com.example.zhidao.pojo.entity.AIAnswer;

import java.util.List;

public interface AIAnswerService {
    AIAnswer createAIAnswer(Long issueId, String question);

    AIAnswer getAIAnswer(Long issueId);

    void likedAIAnswer(Long aiAnswerId,String username);

    void unlikedAIAnswer(Long aiAnswerId,String username);

    void collectAIAnswer(String username, Long aiAnswerId);

    void unCollectAIAnswer(String username, Long aiAnswerId);

    void createAIAnswerComment(String username, Long aiAnswerId, String content);

    void deleteAIAnswerComment(String username, Long commentId);

    List<AIAnswer> getMyCollectAIAnswer(String username, Integer page, Integer pageSize);
}
