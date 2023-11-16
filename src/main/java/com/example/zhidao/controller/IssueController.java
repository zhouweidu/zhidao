package com.example.zhidao.controller;

import com.example.zhidao.mapper.IssueMapper;
import com.example.zhidao.pojo.entity.AIAnswer;
import com.example.zhidao.pojo.entity.Issue;
import com.example.zhidao.pojo.vo.common.ResultResponse;
import com.example.zhidao.pojo.vo.issue.CreateIssueRequest;
import com.example.zhidao.pojo.vo.issue.FindIssuePagesRequest;
import com.example.zhidao.pojo.vo.issue.IssueVO;
import com.example.zhidao.service.AIAnswerService;
import com.example.zhidao.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class IssueController {
    @Autowired
    private IssueService issueService;

    @Autowired
    private AIAnswerService aiAnswerService;

    @PostMapping("/issue")
    public ResultResponse createIssue(@Valid @RequestBody CreateIssueRequest createIssueRequest) {
        Issue issue = issueService.createIssue(createIssueRequest.getUsername(),
                createIssueRequest.getIssueTitle(),
                createIssueRequest.getIssueContent(), createIssueRequest.getIssueImages());
        AIAnswer aiAnswer = aiAnswerService.createAIAnswer(issue.getIssueId(),
                issue.getIssueTitle() + " " + issue.getIssueContent());
        return ResultResponse.success(IssueMapper.INSTANCT.entity2VO(issue, aiAnswer));
    }

    @GetMapping("/issue/page")
    public ResultResponse findIssuePages(@Valid FindIssuePagesRequest findIssuePagesRequest) {
        List<Issue> issuePages = issueService.findIssuePages(findIssuePagesRequest.getPage(),
                findIssuePagesRequest.getPageSize());
        ArrayList<IssueVO> issueVOList = new ArrayList<>();
        for (Issue issuePage : issuePages) {
            issueVOList.add(IssueMapper.INSTANCT.entity2VO(issuePage));
        }
        return ResultResponse.success(issueVOList);
    }

}
