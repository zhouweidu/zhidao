package com.example.zhidao.service;

import com.example.zhidao.pojo.entity.AnswerImage;

import java.util.List;

public interface AnswerImageService {
    List<AnswerImage> createAnswerImages(Long answerId, List<String> answerImages);
    List<AnswerImage> findAnswerImagesByAnswerId(Long answerId);
    void deleteAnswerImages(Long answerId);
}
