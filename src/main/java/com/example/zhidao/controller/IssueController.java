package com.example.zhidao.controller;

import com.example.zhidao.dao.ConcernedIssueRepository;
import com.example.zhidao.mapper.IssueMapper;
import com.example.zhidao.pojo.entity.*;
import com.example.zhidao.pojo.vo.common.ResultResponse;
import com.example.zhidao.pojo.vo.issue.*;
import com.example.zhidao.service.AIAnswerService;
import com.example.zhidao.service.IssueImageService;
import com.example.zhidao.service.IssueService;
import com.example.zhidao.service.UserService;
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

    @Autowired
    private ConcernedIssueRepository concernedIssueRepository;

    @Autowired
    private UserService userService;

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

    @GetMapping("/issue")
    public ResultResponse findIssueByIssueId(Long issueId,String username){
        Issue issue = issueService.findById(issueId);
        User userInfo = userService.findUserInfo(issue.getUserId());
        ConcernedIssue concernedIssue = concernedIssueRepository.findByUserIdAndIssueId(
                userService.findUserByUsername(username).getUserId(), issueId);
        return ResultResponse.success(IssueMapper.INSTANCT.entity2VO(issue,null,userInfo.getNickName(),
                concernedIssue != null));
    }

    @GetMapping("/issue/page")
    public ResultResponse findIssuePages(@Valid FindIssuePagesRequest findIssuePagesRequest) {
        List<Issue> issuePages = issueService.findIssuePages(findIssuePagesRequest.getPage(),
                findIssuePagesRequest.getPageSize());
        User requestUser = userService.findUserByUsername(findIssuePagesRequest.getUsername());
        ArrayList<IssueVO> issueVOList = new ArrayList<>();
        for (Issue issuePage : issuePages) {
            List<IssueImage> issueImages = issueImageService.findIssueImagesByIssueId(issuePage.getIssueId());
            List<String> issueImagePaths = new ArrayList<>();
            if (issueImages != null && issueImages.size() > 0) {
                for (IssueImage issueImage : issueImages) {
                    issueImagePaths.add(issueImage.getImagePath());
                }
            }
            User userInfo = userService.findUserInfo(issuePage.getUserId());
            ConcernedIssue concernedIssue = concernedIssueRepository.findByUserIdAndIssueId(
                    requestUser.getUserId(), issuePage.getIssueId());
            issueVOList.add(IssueMapper.INSTANCT.entity2VO(issuePage, issueImagePaths,userInfo.getNickName(),
                    concernedIssue!= null));
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
            User userInfo = userService.findUserInfo(concernIssue.getUserId());
            issueVOList.add(IssueMapper.INSTANCT.entity2VO(concernIssue, issueImagePaths,userInfo.getNickName()));
        }
        return ResultResponse.success(issueVOList);
    }

    @GetMapping("/issue/myIssue")
    public ResultResponse findMyIssue(@Valid FindConcernOrSubmitIssuePagesRequest request) {
        List<Issue> myIssues = issueService.findMyIssue(
                request.getUsername(), request.getPage(), request.getPageSize());
        User requestUser = userService.findUserByUsername(request.getUsername());
        ArrayList<IssueVO> issueVOList = new ArrayList<>();
        for (Issue myIssue : myIssues) {
            List<IssueImage> issueImages = issueImageService.findIssueImagesByIssueId(myIssue.getIssueId());
            List<String> issueImagePaths = new ArrayList<>();
            if (issueImages != null && issueImages.size() > 0) {
                for (IssueImage issueImage : issueImages) {
                    issueImagePaths.add(issueImage.getImagePath());
                }
            }
            ConcernedIssue concernedIssue = concernedIssueRepository.findByUserIdAndIssueId(
                    requestUser.getUserId(), myIssue.getIssueId());
            issueVOList.add(IssueMapper.INSTANCT.entity2VO(myIssue, issueImagePaths,requestUser.getNickName(),
                    concernedIssue != null));
        }
        return ResultResponse.success(issueVOList);
    }
}