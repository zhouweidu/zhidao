package com.example.zhidao.service;

import com.example.zhidao.pojo.entity.AIAnswer;

public interface AIAnswerService {
    AIAnswer createAIAnswer(Long issueId, String question);

    AIAnswer getAIAnswer(Long issueId);
}
