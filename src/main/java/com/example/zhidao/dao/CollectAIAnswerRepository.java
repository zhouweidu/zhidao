package com.example.zhidao.dao;

import com.example.zhidao.pojo.entity.CollectAIAnswer;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollectAIAnswerRepository extends JpaRepository<CollectAIAnswer,Long> {
    void deleteByUserIdAndAiAnswerId(Long userId, Long aiAnswerId);

    List<CollectAIAnswer> findAllByUserId(Long userId, PageRequest pageRequest);
}
