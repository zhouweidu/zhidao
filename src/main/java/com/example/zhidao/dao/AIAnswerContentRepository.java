package com.example.zhidao.dao;

import com.example.zhidao.pojo.entity.AIAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AIAnswerContentRepository extends JpaRepository<AIAnswer,Long> {
}
