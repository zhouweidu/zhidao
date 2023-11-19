package com.example.zhidao.service;

import com.example.zhidao.pojo.entity.Issue;

import java.util.List;

public interface IssueService {
    Issue createIssue(String username, String issueTitle, String issueContent);
    List<Issue> findIssuePages(Integer page,Integer pageSize);

    void concernIssue(String username, Long issueId);

    void cancelConcernIssue(String username, Long issueId);

    List<Issue> findMyConcernIssue(String username);

    List<Issue> findMyIssue(String username);
}
