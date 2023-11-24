package com.example.zhidao.controller;

import com.example.zhidao.mapper.AIAnswerMapper;
import com.example.zhidao.pojo.entity.AIAnswer;
import com.example.zhidao.pojo.vo.aianswer.CollectOrNotRequest;
import com.example.zhidao.pojo.vo.aianswer.CreateAICommentRequest;
import com.example.zhidao.pojo.vo.common.ResultResponse;
import com.example.zhidao.service.AIAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AIAnswerController {
    @Autowired
    private AIAnswerService aiAnswerService;

    @GetMapping("/aiAnswer/{issueId}")
    public ResultResponse getAIAnswer(@PathVariable("issueId") Long issueId) {
        AIAnswer aiAnswer = aiAnswerService.getAIAnswer(issueId);
        return ResultResponse.success(AIAnswerMapper.INSTANCT.entity2VO(aiAnswer));
    }

    @GetMapping("/aiAnswer/liked/{aiAnswerId}")
    public ResultResponse likedAIAnswer(@PathVariable("aiAnswerId") Long aiAnswerId) {
        aiAnswerService.likedAIAnswer(aiAnswerId);
        return ResultResponse.success();
    }

    @GetMapping("/aiAnswer/unliked/{aiAnswerId}")
    public ResultResponse unlikedAIAnswer(@PathVariable("aiAnswerId") Long aiAnswerId) {
        aiAnswerService.unlikedAIAnswer(aiAnswerId);
        return ResultResponse.success();
    }

    @GetMapping("/aiAnswer/collect")
    public ResultResponse collectAIAnswer(@Valid CollectOrNotRequest collectOrNotRequest) {
        aiAnswerService.collectAIAnswer(collectOrNotRequest.getUsername(), collectOrNotRequest.getAiAnswerId());
        return ResultResponse.success();
    }

    @GetMapping("/aiAnswer/unCollect")
    public ResultResponse unCollectAIAnswer(@Valid CollectOrNotRequest collectOrNotRequest) {
        aiAnswerService.unCollectAIAnswer(collectOrNotRequest.getUsername(), collectOrNotRequest.getAiAnswerId());
        return ResultResponse.success();
    }

    @PostMapping("/aiAnswer/comment")
    public ResultResponse createAIAnswerComment(@Valid @RequestBody CreateAICommentRequest createCommentRequest) {
        aiAnswerService.createAIAnswerComment(createCommentRequest.getUsername(),
                createCommentRequest.getAiAnswerId(), createCommentRequest.getContent());
        return ResultResponse.success();
    }
}
