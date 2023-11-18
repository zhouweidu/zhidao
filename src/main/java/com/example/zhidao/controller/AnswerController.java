package com.example.zhidao.controller;

import com.example.zhidao.mapper.AnswerMapper;
import com.example.zhidao.pojo.entity.Answer;
import com.example.zhidao.pojo.vo.answer.AnswerVO;
import com.example.zhidao.pojo.vo.answer.CreateAnswerRequest;
import com.example.zhidao.pojo.vo.answer.FindAnswerPagesRequest;
import com.example.zhidao.pojo.vo.common.ResultResponse;
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

    @GetMapping("/answer/page")
    public ResultResponse findAnswerPages(@Valid FindAnswerPagesRequest findAnswerPagesRequest) {
        List<Answer> answers = answerService.findAnswerPages(findAnswerPagesRequest.getIssueId(),
                findAnswerPagesRequest.getPage(), findAnswerPagesRequest.getPageSize());
        ArrayList<AnswerVO> answerVOList = new ArrayList<>();
        for (Answer answer : answers) {
            answerVOList.add(AnswerMapper.INSTANCT.entity2VO(answer));
        }
        return ResultResponse.success(answerVOList);
    }

    @PostMapping("/answer")
    public ResultResponse createAnswer(@Valid @RequestBody CreateAnswerRequest createAnswerRequest) {
        Answer answer = answerService.createAnswer(createAnswerRequest.getUsername(), createAnswerRequest.getIssueId()
                , createAnswerRequest.getAnswerContent(), createAnswerRequest.getAnswerImages());
        return ResultResponse.success(AnswerMapper.INSTANCT.entity2VO(answer));
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
