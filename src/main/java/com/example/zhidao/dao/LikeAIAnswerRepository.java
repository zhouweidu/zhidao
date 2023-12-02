package com.example.zhidao.dao;

import com.example.zhidao.pojo.entity.LikeAIAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeAIAnswerRepository extends JpaRepository<LikeAIAnswer,Long>{
    void deleteByAiAnswerIdAndUserId(Long aiAnswerId, Long userId);

    LikeAIAnswer findByAiAnswerIdAndUserId(Long aiAnswerId, Long userId);
}
