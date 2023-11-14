package com.example.zhidao.service.impl;

import com.example.zhidao.dao.AIAnswerRepository;
import com.example.zhidao.pojo.entity.AIAnswer;
import com.example.zhidao.service.AIAnswerService;
import com.example.zhidao.service.XfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AIAnswerServiceImpl implements AIAnswerService {
    @Value("${xf.aiId}")
    private Integer aiId;
    @Autowired
    private XfService xfService;
    @Autowired
    private AIAnswerRepository aiAnswerRepository;

    @Override
    public AIAnswer createAIAnswer(Long issueId, String question) {
        String aiAnswerString = xfService.sendQuestionToXf(question);
        return aiAnswerRepository.save(AIAnswer.builder().aiId(aiId).issueId(issueId)
                .aiAnswerContent(aiAnswerString).likedNumber(0)
                .commentNumber(0).collectNumber(0).build());
    }

    @Override
    public AIAnswer getAIAnswer(Long issueId) {
        return aiAnswerRepository.findAIAnswerByIssueId(issueId);
    }
}
