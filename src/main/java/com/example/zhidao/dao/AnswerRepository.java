package com.example.zhidao.dao;

import com.example.zhidao.pojo.entity.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Page<Answer> findByIssueId(Long issueId, Pageable pageable);
}
