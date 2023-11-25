package com.example.zhidao.dao;

import com.example.zhidao.pojo.entity.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    List<Issue> findByUserId(Long userId, Pageable pageable);

    Page<Issue> findByIssueTitleLikeOrIssueContentLike(String issueTitle, String issueContent, Pageable pageable);
}
