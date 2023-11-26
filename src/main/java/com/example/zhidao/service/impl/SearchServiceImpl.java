package com.example.zhidao.service.impl;

import com.example.zhidao.dao.IssueRepository;
import com.example.zhidao.pojo.entity.Issue;
import com.example.zhidao.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private IssueRepository issueRepository;

    @Override
    public List<Issue> search(String keyword, Integer page, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC,
                "answerNumber"));
        return issueRepository.findByIssueTitleLikeOrIssueContentLike(
                "%" + keyword + "%", "%" + keyword + "%", pageRequest).getContent();
    }
}
