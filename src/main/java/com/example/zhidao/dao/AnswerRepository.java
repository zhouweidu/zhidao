package com.example.zhidao.dao;

import com.example.zhidao.pojo.entity.Answer;
import com.example.zhidao.pojo.entity.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Page<Answer> findByIssueId(Long issueId, Pageable pageable);

    List<Answer> findByUserId(Long userId, Pageable pageable);

    List<Answer> findByUserIdIn(List<Long> followedUserIds, Pageable pageable);
}
