package com.example.zhidao.controller;

import com.example.zhidao.mapper.AIAnswerMapper;
import com.example.zhidao.pojo.entity.AIAnswer;
import com.example.zhidao.pojo.vo.aianswer.*;
import com.example.zhidao.pojo.vo.common.ResultResponse;
import com.example.zhidao.service.AIAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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

    @PostMapping("/aiAnswer/vote/{aiAnswerId}")
    public ResultResponse likedAIAnswer(@PathVariable("aiAnswerId") Long aiAnswerId) {
        aiAnswerService.likedAIAnswer(aiAnswerId);
        return ResultResponse.success();
    }

    @DeleteMapping("/aiAnswer/vote/{aiAnswerId}")
    public ResultResponse unlikedAIAnswer(@PathVariable("aiAnswerId") Long aiAnswerId) {
        aiAnswerService.unlikedAIAnswer(aiAnswerId);
        return ResultResponse.success();
    }

    @PostMapping("/aiAnswer/collect")
    public ResultResponse collectAIAnswer(@Valid CollectOrNotRequest collectOrNotRequest) {
        aiAnswerService.collectAIAnswer(collectOrNotRequest.getUsername(), collectOrNotRequest.getAiAnswerId());
        return ResultResponse.success();
    }

    @DeleteMapping("/aiAnswer/collect")
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

    @DeleteMapping("/aiAnswer/comment")
    public ResultResponse deleteAIAnswerComment(@Valid DeleteAIAnswerCommentRequest deleteAIAnswerCommentRequest) {
        aiAnswerService.deleteAIAnswerComment(deleteAIAnswerCommentRequest.getUsername(),
                deleteAIAnswerCommentRequest.getId());
        return ResultResponse.success();
    }

    @GetMapping("/aiAnswer/myCollect")
    public ResultResponse getMyCollectAIAnswer(@Valid FindAIAnswerCollectRequest request) {
        List<AIAnswer> myCollectAIAnswer = aiAnswerService.getMyCollectAIAnswer(request.getUsername(),
                request.getPage(), request.getPageSize());
        ArrayList<AIAnswerVO> aiAnswerVOList = new ArrayList<>();
        for (AIAnswer aiAnswer : myCollectAIAnswer) {
            aiAnswerVOList.add(AIAnswerMapper.INSTANCT.entity2VO(aiAnswer));
        }
        return ResultResponse.success(aiAnswerVOList);
    }

}
