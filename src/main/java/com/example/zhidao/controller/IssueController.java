package com.example.zhidao.controller;

import com.example.zhidao.mapper.IssueMapper;
import com.example.zhidao.pojo.entity.AIAnswer;
import com.example.zhidao.pojo.entity.Issue;
import com.example.zhidao.pojo.entity.IssueImage;
import com.example.zhidao.pojo.vo.common.ResultResponse;
import com.example.zhidao.pojo.vo.issue.ConcernOrNotRequest;
import com.example.zhidao.pojo.vo.issue.CreateIssueRequest;
import com.example.zhidao.pojo.vo.issue.FindIssuePagesRequest;
import com.example.zhidao.pojo.vo.issue.IssueVO;
import com.example.zhidao.pojo.vo.issue.FindConcernOrSubmitIssuePagesRequest;
import com.example.zhidao.service.AIAnswerService;
import com.example.zhidao.service.IssueImageService;
import com.example.zhidao.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    private IssueImageService issueImageService;

    @PostMapping("/issue")
    @Transactional
    public ResultResponse createIssue(@Valid @RequestBody CreateIssueRequest createIssueRequest) {
        Issue issue = issueService.createIssue(createIssueRequest.getUsername(),
                createIssueRequest.getIssueTitle(),
                createIssueRequest.getIssueContent());
        AIAnswer aiAnswer = aiAnswerService.createAIAnswer(issue.getIssueId(),
                issue.getIssueTitle() + " " + issue.getIssueContent());
        issueImageService.createIssueImages(issue.getIssueId(), createIssueRequest.getIssueImages());
        return ResultResponse.success(IssueMapper.INSTANCT.entity2VO(issue, aiAnswer));
    }

    @GetMapping("/issue/page")
    public ResultResponse findIssuePages(@Valid FindIssuePagesRequest findIssuePagesRequest) {
        List<Issue> issuePages = issueService.findIssuePages(findIssuePagesRequest.getPage(),
                findIssuePagesRequest.getPageSize());
        ArrayList<IssueVO> issueVOList = new ArrayList<>();
        for (Issue issuePage : issuePages) {
            List<IssueImage> issueImages = issueImageService.findIssueImagesByIssueId(issuePage.getIssueId());
            List<String> issueImagePaths = new ArrayList<>();
            if (issueImages != null && issueImages.size() > 0) {
                for (IssueImage issueImage : issueImages) {
                    issueImagePaths.add(issueImage.getImagePath());
                }
            }
            issueVOList.add(IssueMapper.INSTANCT.entity2VO(issuePage, issueImagePaths));
        }
        return ResultResponse.success(issueVOList);
    }

    @PostMapping("/issue/concern")
    public ResultResponse concernIssue(@Valid ConcernOrNotRequest concernOrNotRequest) {
        issueService.concernIssue(concernOrNotRequest.getUsername(), concernOrNotRequest.getIssueId());
        return ResultResponse.success();
    }

    @DeleteMapping("/issue/concern")
    public ResultResponse unConcernIssue(@Valid ConcernOrNotRequest concernOrNotRequest) {
        issueService.unConcernIssue(concernOrNotRequest.getUsername(), concernOrNotRequest.getIssueId());
        return ResultResponse.success();
    }

    @GetMapping("/issue/myConcern")
    public ResultResponse findMyConcernIssue(@Valid FindConcernOrSubmitIssuePagesRequest reqeust) {
        List<Issue> concernIssues = issueService.findMyConcernIssue(reqeust.getUsername(),
                reqeust.getPage(), reqeust.getPageSize());
        ArrayList<IssueVO> issueVOList = new ArrayList<>();
        for (Issue concernIssue : concernIssues) {
            List<IssueImage> issueImages = issueImageService.findIssueImagesByIssueId(concernIssue.getIssueId());
            List<String> issueImagePaths = new ArrayList<>();
            if (issueImages != null && issueImages.size() > 0) {
                for (IssueImage issueImage : issueImages) {
                    issueImagePaths.add(issueImage.getImagePath());
                }
            }
            issueVOList.add(IssueMapper.INSTANCT.entity2VO(concernIssue, issueImagePaths));
        }
        return ResultResponse.success(issueVOList);
    }

    @GetMapping("/issue/myIssue")
    public ResultResponse findMyIssue(@Valid FindConcernOrSubmitIssuePagesRequest request) {
        List<Issue> myIssues = issueService.findMyIssue(
                request.getUsername(), request.getPage(), request.getPageSize());
        ArrayList<IssueVO> issueVOList = new ArrayList<>();
        for (Issue myIssue : myIssues) {
            List<IssueImage> issueImages = issueImageService.findIssueImagesByIssueId(myIssue.getIssueId());
            List<String> issueImagePaths = new ArrayList<>();
            if (issueImages != null && issueImages.size() > 0) {
                for (IssueImage issueImage : issueImages) {
                    issueImagePaths.add(issueImage.getImagePath());
                }
            }
            issueVOList.add(IssueMapper.INSTANCT.entity2VO(myIssue, issueImagePaths));
        }
        return ResultResponse.success(issueVOList);
    }
}