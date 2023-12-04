package com.example.zhidao.dao;

import com.example.zhidao.pojo.entity.Answer;
import com.example.zhidao.pojo.entity.CollectAnswer;
import com.example.zhidao.pojo.entity.Issue;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollectAnswerRepository extends JpaRepository<CollectAnswer,Long> {
    void deleteByUserIdAndAnswerId(Long userId, Long answerId);

    List<CollectAnswer> findByUserId(Long userId, Pageable pageable);

    CollectAnswer findByAnswerIdAndUserId(Long answerId,Long userId);
}
