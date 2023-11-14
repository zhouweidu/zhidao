package com.example.zhidao.dao;

import com.example.zhidao.pojo.entity.AIAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AIAnswerRepository extends JpaRepository<AIAnswer, Long> {
    AIAnswer findAIAnswerByIssueId(Long issueId);
}
