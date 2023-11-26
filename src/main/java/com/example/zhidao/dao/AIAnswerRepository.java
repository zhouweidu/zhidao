package com.example.zhidao.dao;

import com.example.zhidao.pojo.entity.AIAnswer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AIAnswerRepository extends JpaRepository<AIAnswer, Long> {
    AIAnswer findAIAnswerByIssueId(Long issueId);
}
