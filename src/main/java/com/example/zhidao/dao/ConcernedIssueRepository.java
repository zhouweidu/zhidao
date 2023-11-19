package com.example.zhidao.dao;

import com.example.zhidao.pojo.entity.ConcernedIssue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConcernedIssueRepository extends JpaRepository<ConcernedIssue,Long> {
    void deleteByUserIdAndIssueId(Long userId, Long issueId);

    List<ConcernedIssue> findByUserId(Long userId);
}
