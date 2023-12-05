package com.example.zhidao.controller;

import com.example.zhidao.dao.ConcernedIssueRepository;
import com.example.zhidao.mapper.IssueMapper;
import com.example.zhidao.pojo.entity.ConcernedIssue;
import com.example.zhidao.pojo.entity.Issue;
import com.example.zhidao.pojo.entity.User;
import com.example.zhidao.pojo.vo.common.ResultResponse;
import com.example.zhidao.pojo.vo.issue.IssueVO;
import com.example.zhidao.pojo.vo.search.SearchRequest;
import com.example.zhidao.service.SearchService;
import com.example.zhidao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SearchController {
    @Autowired
    private SearchService searchService;
    @Autowired
    private ConcernedIssueRepository concernedIssueRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/search")
    public ResultResponse search(@Valid SearchRequest searchRequest) {
        List<Issue> search = searchService.search(searchRequest.getKeyword(),
                searchRequest.getPage(), searchRequest.getPageSize());
        User requestUser = userService.findUserByUsername(searchRequest.getUsername());
        ArrayList<IssueVO> issueVOList = new ArrayList<>();
        for (Issue issue : search) {
            ConcernedIssue concernedIssue = concernedIssueRepository.findByUserIdAndIssueId(
                    requestUser.getUserId(), issue.getIssueId());
            issueVOList.add(IssueMapper.INSTANCT.entity2VO(issue, new ArrayList<>(),
                    userService.findUserInfo(issue.getUserId()).getNickName(),concernedIssue!=null));
        }
        return ResultResponse.success(issueVOList);
    }
}
