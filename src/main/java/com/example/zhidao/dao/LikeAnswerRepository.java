package com.example.zhidao.dao;

import com.example.zhidao.pojo.entity.LikeAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeAnswerRepository extends JpaRepository<LikeAnswer,Long> {
    void deleteByAnswerIdAndUserId(Long answerId, Long userId);

    LikeAnswer findByAnswerIdAndUserId(Long answerId, Long userId);
}
