package com.example.zhidao.dao;

import com.example.zhidao.pojo.entity.ConcernedIssue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcernedIssueRepository extends JpaRepository<ConcernedIssue,Long> {
    void deleteByUserIdAndIssueId(Long userId, Long issueId);

    Page<ConcernedIssue> findByUserId(Long userId, Pageable pageable);
}
