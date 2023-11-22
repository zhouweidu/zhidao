package com.example.zhidao.dao;

import com.example.zhidao.pojo.entity.CollectAIAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectAIAnswerRepository extends JpaRepository<CollectAIAnswer,Long> {
    void deleteByUserIdAndAiAnswerId(Long userId, Long aiAnswerId);
}