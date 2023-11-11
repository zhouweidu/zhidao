package com.example.zhidao.service.impl;

import com.example.zhidao.dao.IssueRepository;
import com.example.zhidao.pojo.entity.Issue;
import com.example.zhidao.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IssueServiceImpl implements IssueService {
    @Autowired
    private IssueRepository issueRepository;
    @Override
    public Issue createIssue(Long userId) {
        Issue issue = Issue.builder().userId(userId).answerNumber(1).concernedNumber(0).build();
        return issueRepository.save(issue);
    }
}
