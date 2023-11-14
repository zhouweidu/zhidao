package com.example.zhidao.service;

import com.example.zhidao.pojo.entity.Issue;

import java.util.List;

public interface IssueService {
    Issue createIssue(String username, String issueTitle, String issueContent, List<String> issueImages);
    List<Issue> findIssuePages(Integer page,Integer pageSize);
}
