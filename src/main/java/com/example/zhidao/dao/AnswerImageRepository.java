package com.example.zhidao.dao;

import com.example.zhidao.pojo.entity.AnswerImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerImageRepository extends JpaRepository<AnswerImage, String> {
    void deleteByAnswerId(Long answerId);

    List<AnswerImage> findAnswerImagesByAnswerId(Long answerId);

}
