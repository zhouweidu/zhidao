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
    @Autowired
    private IssueImageRepository issueImageRepository;

    @Override
    @Transactional
    public Issue createIssue(String username, String issueTitle, String issueContent, List<String> issueImages) {
        User user = userRepository.findUserByUsername(username);
        Issue issue = issueRepository.save(Issue.builder().userId(user.getUserId()).issueTitle(issueTitle)
                .issueContent(issueContent).concernedNumber(0).answerNumber(0).build());
        if (issueImages != null && issueImages.size() != 0) {
            ArrayList<IssueImage> imageList = new ArrayList<>();
            for (String issueImage : issueImages) {
                imageList.add(IssueImage.builder().issueId(issue.getIssueId()).imagePath(issueImage).build());
            }
            issueImageRepository.saveAll(imageList);
        }
        return issue;
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
        Page<Issue> issuePages = issueRepository.findBy(pageRequest);
        ArrayList<Issue> issues = new ArrayList<>();
        for (Issue issuePage : issuePages) {
            issues.add(issuePage);
        }
        return issues;
    }
}
