package com.example.zhidao.controller;

import com.example.zhidao.mapper.AnswerMapper;
import com.example.zhidao.pojo.entity.Answer;
import com.example.zhidao.pojo.entity.AnswerImage;
import com.example.zhidao.pojo.vo.answer.AnswerVO;
import com.example.zhidao.pojo.vo.answer.CreateAnswerRequest;
import com.example.zhidao.pojo.vo.answer.FindAnswerPagesRequest;
import com.example.zhidao.pojo.vo.common.ResultResponse;
import com.example.zhidao.service.AnswerImageService;
import com.example.zhidao.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AnswerController {
    @Autowired
    private AnswerService answerService;
    @Autowired
    private AnswerImageService answerImageService;

    @GetMapping("/answer/page")
    public ResultResponse findAnswerPages(@Valid FindAnswerPagesRequest findAnswerPagesRequest) {
        List<Answer> answers = answerService.findAnswerPages(findAnswerPagesRequest.getIssueId(),
                findAnswerPagesRequest.getPage(), findAnswerPagesRequest.getPageSize());
        ArrayList<AnswerVO> answerVOList = new ArrayList<>();
        for (Answer answer : answers) {
            List<AnswerImage> answerImages = answerImageService.findAnswerImagesByAnswerId(answer.getAnswerId());
            ArrayList<String> answerImagePaths = new ArrayList<>();
            for (AnswerImage answerImage : answerImages) {
                answerImagePaths.add(answerImage.getImagePath());
            }
            answerVOList.add(AnswerMapper.INSTANCT.entity2VO(answer, answerImagePaths));
        }
        return ResultResponse.success(answerVOList);
    }

    @PostMapping("/answer")
    public ResultResponse createAnswer(@Valid @RequestBody CreateAnswerRequest createAnswerRequest) {
        Answer answer = answerService.createAnswer(createAnswerRequest.getUsername(),
                createAnswerRequest.getIssueId(), createAnswerRequest.getAnswerContent());
        List<AnswerImage> answerImages = answerImageService.createAnswerImages(answer.getAnswerId(),
                createAnswerRequest.getAnswerImages());
        ArrayList<String> answerImagePaths = new ArrayList<>();
        for (AnswerImage answerImage : answerImages) {
            answerImagePaths.add(answerImage.getImagePath());
        }
        return ResultResponse.success(AnswerMapper.INSTANCT.entity2VO(answer, answerImagePaths));
    }

    @DeleteMapping("/answer/{answerId}")
    public ResultResponse deleteAnswer(@PathVariable("answerId") Long answerId) {
        answerService.deleteAnswer(answerId);
        answerImageService.deleteAnswerImages(answerId);
        return ResultResponse.success();
    }

    @GetMapping("/answer/liked/{answerId}")
    public ResultResponse likedAnswer(@PathVariable("answerId") Long answerId) {
        answerService.likedAnswer(answerId);
        return ResultResponse.success();
    }

    @GetMapping("/answer/unliked/{answerId}")
    public ResultResponse unlikedAnswer(@PathVariable("answerId") Long answerId) {
        answerService.unlikedAnswer(answerId);
        return ResultResponse.success();
    }
}
