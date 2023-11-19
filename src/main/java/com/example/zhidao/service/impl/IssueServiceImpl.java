package com.example.zhidao.service.impl;

import com.example.zhidao.dao.ConcernedIssueRepository;
import com.example.zhidao.dao.IssueRepository;
import com.example.zhidao.dao.UserRepository;
import com.example.zhidao.pojo.entity.ConcernedIssue;
import com.example.zhidao.pojo.entity.Issue;
import com.example.zhidao.pojo.entity.User;
import com.example.zhidao.pojo.vo.common.BizException;
import com.example.zhidao.pojo.vo.common.ExceptionEnum;
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
    @Autowired
    private ConcernedIssueRepository concernedIssueRepository;

    @Override
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

    @Transactional
    @Override
    public void concernIssue(String username, Long issueId) {
        User user = userRepository.findUserByUsername(username);
        Issue issue = issueRepository.findById(issueId).get();
        issue.setConcernedNumber(issue.getConcernedNumber() + 1);
        issueRepository.save(issue);
        concernedIssueRepository.save(ConcernedIssue.builder().issueId(issueId)
                .userId(user.getUserId()).build());
    }

    @Transactional
    @Override
    public void cancelConcernIssue(String username, Long issueId) {
        User user = userRepository.findUserByUsername(username);
        Issue issue = issueRepository.findById(issueId).get();
        issue.setConcernedNumber(issue.getConcernedNumber() - 1);
        issueRepository.save(issue);
        concernedIssueRepository.deleteByUserIdAndIssueId(user.getUserId(), issueId);
    }

    @Override
    public List<Issue> findMyConcernIssue(String username) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new BizException(ExceptionEnum.USER_NOT_FOUND);
        }
        List<ConcernedIssue> concernedIssues = concernedIssueRepository.findByUserId(user.getUserId());
        ArrayList<Issue> issues = new ArrayList<>();
        for (ConcernedIssue concernedIssue : concernedIssues) {
            issues.add(issueRepository.findById(concernedIssue.getIssueId()).get());
        }
        return issues;
    }

    @Override
    public List<Issue> findMyIssue(String username) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new BizException(ExceptionEnum.USER_NOT_FOUND);
        }
        return issueRepository.findByUserId(user.getUserId());
    }
}
