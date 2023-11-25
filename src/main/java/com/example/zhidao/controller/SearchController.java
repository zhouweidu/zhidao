package com.example.zhidao.controller;

import com.example.zhidao.mapper.IssueMapper;
import com.example.zhidao.pojo.entity.Issue;
import com.example.zhidao.pojo.vo.common.ResultResponse;
import com.example.zhidao.pojo.vo.issue.IssueVO;
import com.example.zhidao.pojo.vo.search.SearchRequest;
import com.example.zhidao.service.SearchService;
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
    @GetMapping("/search")
    public ResultResponse search(@Valid SearchRequest searchRequest) {
        List<Issue> search = searchService.search(searchRequest.getIssueTitle(), searchRequest.getPage(), searchRequest.getPageSize());
        ArrayList<IssueVO> issueVOList = new ArrayList<>();
        for (Issue issue : search) {
            issueVOList.add(IssueMapper.INSTANCT.entity2VO(issue,new ArrayList<>()));
        }
        return ResultResponse.success(issueVOList);
    }
}
