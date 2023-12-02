package com.example.zhidao.controller;

import com.example.zhidao.dao.LikeAIAnswerRepository;
import com.example.zhidao.mapper.AIAnswerMapper;
import com.example.zhidao.pojo.entity.AIAnswer;
import com.example.zhidao.pojo.entity.LikeAIAnswer;
import com.example.zhidao.pojo.entity.User;
import com.example.zhidao.pojo.vo.aianswer.*;
import com.example.zhidao.pojo.vo.common.ResultResponse;
import com.example.zhidao.service.AIAnswerService;
import com.example.zhidao.service.UserService;
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
    @Autowired
    private LikeAIAnswerRepository likeAIAnswerRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/aiAnswer")
    public ResultResponse getAIAnswer(Long issueId,String username) {
        AIAnswer aiAnswer = aiAnswerService.getAIAnswer(issueId);
        LikeAIAnswer likeAIAnswer = likeAIAnswerRepository.findByAiAnswerIdAndUserId(aiAnswer.getAiAnswerId(),
                userService.findUserByUsername(username).getUserId());
        return ResultResponse.success(AIAnswerMapper.INSTANCT.entity2VO(aiAnswer,likeAIAnswer!=null));
    }

    @PostMapping("/aiAnswer/vote")
    public ResultResponse likedAIAnswer(LikeOrNotLikeAIAnswerRequest request) {
        aiAnswerService.likedAIAnswer(request.getAiAnswerId(),request.getUsername());
        return ResultResponse.success();
    }

    @DeleteMapping("/aiAnswer/vote")
    public ResultResponse unlikedAIAnswer(LikeOrNotLikeAIAnswerRequest request) {
        aiAnswerService.unlikedAIAnswer(request.getAiAnswerId(),request.getUsername());
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
        User user = userService.findUserByUsername(request.getUsername());
        ArrayList<AIAnswerVO> aiAnswerVOList = new ArrayList<>();
        for (AIAnswer aiAnswer : myCollectAIAnswer) {
            LikeAIAnswer likeAIAnswer = likeAIAnswerRepository.findByAiAnswerIdAndUserId(
                    aiAnswer.getAiAnswerId(), user.getUserId());
            aiAnswerVOList.add(AIAnswerMapper.INSTANCT.entity2VO(aiAnswer,likeAIAnswer!=null));
        }
        return ResultResponse.success(aiAnswerVOList);
    }

}
