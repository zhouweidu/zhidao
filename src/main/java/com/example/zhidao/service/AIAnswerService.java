package com.example.zhidao.service;

import com.example.zhidao.pojo.entity.AIAnswer;

public interface AIAnswerService {
    AIAnswer createAIAnswer(Long issueId, String question);

    AIAnswer getAIAnswer(Long issueId);

    void likedAIAnswer(Long aiAnswerId);

    void unlikedAIAnswer(Long aiAnswerId);

    void collectAIAnswer(String username,Long aiAnswerId);

    void unCollectAIAnswer(String username, Long aiAnswerId);

    void createAIAnswerComment(String username, Long aiAnswerId, String content);
}
