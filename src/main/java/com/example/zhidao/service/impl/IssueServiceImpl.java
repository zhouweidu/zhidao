package com.example.zhidao.service.impl;

import com.example.zhidao.dao.IssueImageRepository;
import com.example.zhidao.dao.IssueRepository;
import com.example.zhidao.dao.UserRepository;
import com.example.zhidao.pojo.entity.Issue;
import com.example.zhidao.pojo.entity.IssueImage;
import com.example.zhidao.pojo.entity.User;
import com.example.zhidao.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class IssueServiceImpl implements IssueService {
    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public Issue createIssue(String username, String issueTitle, String issueContent) {
        User user = userRepository.findUserByUsername(username);
        return issueRepository.save(Issue.builder().userId(user.getUserId()).issueTitle(issueTitle)
                .issueContent(issueContent).concernedNumber(0).answerNumber(0).build());
    }

    /**
     * @param page     页码从0开始
     * @param pageSize 每一页的大小
     * @return
     */
    @Override
    public List<Issue> findIssuePages(Integer page, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC,
                "concernedNumber"));
        Page<Issue> issuePages = issueRepository.findAll(pageRequest);
        ArrayList<Issue> issues = new ArrayList<>();
        for (Issue issuePage : issuePages) {
            issues.add(issuePage);
        }
        return issues;
    }
}
