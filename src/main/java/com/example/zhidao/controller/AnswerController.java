package com.example.zhidao.controller;

import com.example.zhidao.dao.AIAnswerRepository;
import com.example.zhidao.dao.AnswerRepository;
import com.example.zhidao.mapper.AIAnswerMapper;
import com.example.zhidao.mapper.AnswerMapper;
import com.example.zhidao.pojo.entity.*;
import com.example.zhidao.pojo.vo.answer.*;
import com.example.zhidao.pojo.vo.common.ResultResponse;
import com.example.zhidao.service.AnswerImageService;
import com.example.zhidao.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AnswerController {
    @Autowired
    private AnswerService answerService;
    @Autowired
    private AnswerImageService answerImageService;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private AIAnswerRepository aiAnswerRepository;

    @GetMapping("/answer/page")
    public ResultResponse findAnswerPages(@Valid FindAnswerPagesRequest findAnswerPagesRequest) {
        List<Answer> answers = answerService.findAnswerPages(findAnswerPagesRequest.getIssueId(),
                findAnswerPagesRequest.getPage(), findAnswerPagesRequest.getPageSize());
        ArrayList<AnswerVO> answerVOList = new ArrayList<>();
        for (Answer answer : answers) {
            List<AnswerImage> answerImages = answerImageService.findAnswerImagesByAnswerId(answer.getAnswerId());
            ArrayList<String> answerImagePaths = new ArrayList<>();
            if (answerImages != null && answerImages.size() > 0) {
                for (AnswerImage answerImage : answerImages) {
                    answerImagePaths.add(answerImage.getImagePath());
                }
            }
            answerVOList.add(AnswerMapper.INSTANCT.entity2VO(answer, answerImagePaths));
        }
        return ResultResponse.success(answerVOList);
    }

    @PostMapping("/answer")
    @Transactional
    public ResultResponse createAnswer(@Valid @RequestBody CreateAnswerRequest createAnswerRequest) {
        Answer answer = answerService.createAnswer(createAnswerRequest.getUsername(),
                createAnswerRequest.getIssueId(), createAnswerRequest.getAnswerContent());
        List<AnswerImage> answerImages = answerImageService.createAnswerImages(answer.getAnswerId(),
                createAnswerRequest.getAnswerImages());
        ArrayList<String> answerImagePaths = new ArrayList<>();
        if (answerImages != null && answerImages.size() > 0) {
            for (AnswerImage answerImage : answerImages) {
                answerImagePaths.add(answerImage.getImagePath());
            }
        }
        return ResultResponse.success(AnswerMapper.INSTANCT.entity2VO(answer, answerImagePaths));
    }

    @DeleteMapping("/answer/{answerId}")
    @Transactional
    public ResultResponse deleteAnswer(@PathVariable("answerId") Long answerId) {
        answerService.deleteAnswer(answerId);
        answerImageService.deleteAnswerImages(answerId);
        return ResultResponse.success();
    }

    @PostMapping("/answer/vote/{answerId}")
    public ResultResponse likedAnswer(@PathVariable("answerId") Long answerId) {
        answerService.likedAnswer(answerId);
        return ResultResponse.success();
    }

    @DeleteMapping("/answer/vote/{answerId}")
    public ResultResponse unlikedAnswer(@PathVariable("answerId") Long answerId) {
        answerService.unlikedAnswer(answerId);
        return ResultResponse.success();
    }

    @PostMapping("/answer/collect")
    public ResultResponse collectAnswer(@Valid CollectAnswerOrNotRequest collectAnswerOrNotRequest) {
        answerService.collectAnswer(collectAnswerOrNotRequest.getUsername(), collectAnswerOrNotRequest.getAnswerId());
        return ResultResponse.success();
    }

    @DeleteMapping("/answer/collect")
    public ResultResponse unCollectAnswer(@Valid CollectAnswerOrNotRequest collectAnswerOrNotRequest) {
        answerService.unCollectAnswer(collectAnswerOrNotRequest.getUsername(), collectAnswerOrNotRequest.getAnswerId());
        return ResultResponse.success();
    }

    @GetMapping("/answer/myCollected")
    public ResultResponse findMyCollectedAnswers(@Valid FindMyOrCollectedAnswersPagesRequest request) {
        List<Answer> collectedAnswers = answerService.findMyCollectedAnswer(request.getUsername(), request.getPage(), request.getPageSize());
        ArrayList<AnswerVO> answerVOList = new ArrayList<>();
        for (Answer answer : collectedAnswers) {
            List<AnswerImage> answerImages = answerImageService.findAnswerImagesByAnswerId(answer.getAnswerId());
            ArrayList<String> answerImagePaths = new ArrayList<>();
            for (AnswerImage answerImage : answerImages) {
                answerImagePaths.add(answerImage.getImagePath());
            }
            answerVOList.add(AnswerMapper.INSTANCT.entity2VO(answer, answerImagePaths));
        }
        return ResultResponse.success(answerVOList);
    }

    @GetMapping("/answer/myAnswers")
    public ResultResponse findMyAnswers(@Valid FindMyOrCollectedAnswersPagesRequest request) {
        List<Answer> myAnswers = answerService.findMyAnswer(request.getUsername(), request.getPage(), request.getPageSize());
        ArrayList<AnswerVO> answerVOList = new ArrayList<>();
        for (Answer answer : myAnswers) {
            List<AnswerImage> answerImages = answerImageService.findAnswerImagesByAnswerId(answer.getAnswerId());
            ArrayList<String> answerImagePaths = new ArrayList<>();
            for (AnswerImage answerImage : answerImages) {
                answerImagePaths.add(answerImage.getImagePath());
            }
            answerVOList.add(AnswerMapper.INSTANCT.entity2VO(answer, answerImagePaths));
        }
        return ResultResponse.success(answerVOList);
    }

    @GetMapping("/answer/all")
    public ResultResponse findMyCollectedAIAndNormalAnswers(@Valid FindMyCollectedAIAndNormalAnswersPagesRequest request) {
        List<Object> collectedAnswers = answerService.findMyCollectedAIAndNormalAnswers(request.getUsername(), request.getPage(), request.getPageSize());

        ArrayList<AIAnswerOrAnswerVO> aiAnswerOrAnswerVOS = new ArrayList<>();
        for (Object obj : collectedAnswers) {
            if (obj instanceof CollectAIAnswer) {
                // 处理 CollectAIAnswer 对象
                CollectAIAnswer collectAIAnswer = (CollectAIAnswer) obj;
                AIAnswer aiAnswer = aiAnswerRepository.findById(collectAIAnswer.getAiAnswerId()).orElse(null);
                if (aiAnswer != null) {
                    aiAnswerOrAnswerVOS.add(convertAIAnswerToAnswerVO(aiAnswer));
                }
            } else if (obj instanceof CollectAnswer) {
                // 处理 CollectAnswer 对象
                CollectAnswer collectAnswer = (CollectAnswer) obj;
                Answer answer = answerRepository.findById(collectAnswer.getAnswerId()).orElse(null);
                if (answer != null) {
                    List<AnswerImage> answerImages = answerImageService.findAnswerImagesByAnswerId(answer.getAnswerId());
                    ArrayList<String> answerImagePaths = answerImages.stream()
                            .map(AnswerImage::getImagePath)
                            .collect(Collectors.toCollection(ArrayList::new));
                    aiAnswerOrAnswerVOS.add(
                            AIAnswerOrAnswerVO.builder()
                                    .answerId(answer.getAnswerId())
                                    .issueId(answer.getIssueId())
                                    .userId(answer.getUserId())
                                    .answerContent(answer.getAnswerContent())
                                    .likedNumber(answer.getLikedNumber())
                                    .commentNumber(answer.getCommentNumber())
                                    .collectNumber(answer.getCollectNumber())
                                    .createdAt(answer.getCreatedAt().toString())
                                    .answerImages(answerImagePaths)
                                    .isAIAnswer(false)
                                    .build()
                    );
                }
            }
        }
        return ResultResponse.success(aiAnswerOrAnswerVOS);
    }

    private AIAnswerOrAnswerVO convertAIAnswerToAnswerVO(AIAnswer aiAnswer) {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return AIAnswerOrAnswerVO.builder()
                .answerId(aiAnswer.getAiAnswerId())
                .issueId(aiAnswer.getIssueId())
                .userId(null) // 将 AI 答案的 userId 设置为 null
                .answerContent(aiAnswer.getAiAnswerContent())
                .likedNumber(aiAnswer.getLikedNumber())
                .commentNumber(aiAnswer.getCommentNumber())
                .collectNumber(aiAnswer.getCollectNumber())
                .createdAt(ft.format(aiAnswer.getCreatedAt()))
                .answerImages(new ArrayList<>())
                .isAIAnswer(true)
                .build();
    }

}
