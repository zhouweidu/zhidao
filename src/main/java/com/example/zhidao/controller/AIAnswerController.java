package com.example.zhidao.controller;

import com.example.zhidao.mapper.AIAnswerMapper;
import com.example.zhidao.pojo.entity.AIAnswer;
import com.example.zhidao.pojo.vo.common.ResultResponse;
import com.example.zhidao.service.AIAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AIAnswerController {
    @Autowired
    private AIAnswerService aiAnswerService;

    @GetMapping("/aiAnswer")
    public ResultResponse getAIAnswer(@RequestParam Long issueId) {
        AIAnswer aiAnswer = aiAnswerService.getAIAnswer(issueId);
        return ResultResponse.success(AIAnswerMapper.INSTANCT.entity2VO(aiAnswer));
    }
}
