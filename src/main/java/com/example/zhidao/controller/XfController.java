package com.example.zhidao.controller;

import com.example.zhidao.pojo.vo.common.ResultResponse;
import com.example.zhidao.pojo.vo.xf.Question;
import com.example.zhidao.service.XfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class XfController {
    @Autowired
    private XfService xfService;

    @PostMapping("/question")
    public ResultResponse sendQuestion(@Valid @RequestBody Question question) {
        String answer = xfService.sendQuestionToXf(question.getQuestion());
        return ResultResponse.success(answer);
    }
}
