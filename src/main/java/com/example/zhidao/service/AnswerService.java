package com.example.zhidao.service;

import com.example.zhidao.pojo.entity.Answer;

import java.util.List;

public interface AnswerService {
    List<Answer> findAnswerPages(Long issueId, Integer page, Integer pageSize);

    Answer createAnswer(String username, Long issueId, String answerContent);

    void likedAnswer(Long answerId);

    void unlikedAnswer(Long answerId);

    void deleteAnswer(Long answerId);

    void collectAnswer(String username,Long answerId);

    void unCollectAnswer(String username, Long answerId);

    List<Answer> findMyCollectedAnswer(String username, Integer page, Integer pageSize);

    List<Answer> findMyAnswer(String username, Integer page, Integer pageSize);
    List<Answer> findAnswersByFollowedUsers(Long userId, Integer page, Integer pageSize);
    List<Object> findMyCollectedAIAndNormalAnswers(String username, Integer page, Integer pageSize);
}
