package com.example.zhidao.service;

import com.example.zhidao.pojo.entity.Answer;

import java.util.List;

public interface AnswerService {
    List<Answer> findAnswerPages(Long issueId, Integer page, Integer pageSize);

    Answer createAnswer(String username, Long issueId, String answerContent, List<String> answerImages);

    void likedAnswer(Long answerId);

    void unlikedAnswer(Long answerId);
}
