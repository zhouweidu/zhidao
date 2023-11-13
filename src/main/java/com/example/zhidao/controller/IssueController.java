package com.example.zhidao.controller;

import com.example.zhidao.mapper.IssueMapper;
import com.example.zhidao.pojo.entity.Issue;
import com.example.zhidao.pojo.vo.common.ResultResponse;
import com.example.zhidao.pojo.vo.issue.CreateIssueRequest;
import com.example.zhidao.pojo.vo.issue.IssueVO;
import com.example.zhidao.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class IssueController {
    @Autowired
    private IssueService issueService;

    @PostMapping("/issue")
    public ResultResponse createIssue(@Valid @RequestBody CreateIssueRequest createIssueRequest) {
        Issue issue = issueService.createIssue(createIssueRequest.getUsername(), createIssueRequest.getIssueTitle(),
                createIssueRequest.getIssueContent(), createIssueRequest.getIssueImages());
        return ResultResponse.success(IssueMapper.INSTANCT.entity2VO(issue));
    }
}
