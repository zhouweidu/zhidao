package com.example.zhidao.service.impl;

import com.example.zhidao.dao.AnswerRepository;
import com.example.zhidao.pojo.entity.Answer;
import com.example.zhidao.pojo.entity.User;
import com.example.zhidao.pojo.vo.common.BizException;
import com.example.zhidao.pojo.vo.common.ExceptionEnum;
import com.example.zhidao.service.AnswerService;
import com.example.zhidao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private UserService userService;

    @Override
    public List<Answer> findAnswerPages(Long issueId, Integer page, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC,
                "likedNumber"));
        return answerRepository.findByIssueId(issueId, pageRequest).getContent();
    }

    @Override
    public Answer createAnswer(String username, Long issueId, String answerContent) {
        User user = userService.findUserByUsername(username);
        return answerRepository.save(Answer.builder().issueId(issueId).userId(user.getUserId())
                .answerContent(answerContent).likedNumber(0).commentNumber(0).collectNumber(0).build());
    }

    @Override
    public void deleteAnswer(Long answerId) {
        if (answerRepository.findById(answerId).isPresent()) {
            answerRepository.deleteById(answerId);
        } else {
            throw new BizException(ExceptionEnum.ANSWER_NOT_EXIST);
        }
    }

    @Override
    public void likedAnswer(Long answerId) {
        if (answerRepository.findById(answerId).isPresent()) {
            Answer answer = answerRepository.findById(answerId).get();
            answerRepository.save(answer.setLikedNumber(answer.getLikedNumber() + 1));
        } else {
            throw new BizException(ExceptionEnum.ANSWER_NOT_EXIST);
        }
    }

    @Override
    public void unlikedAnswer(Long answerId) {
        if (answerRepository.findById(answerId).isPresent()) {
            Answer answer = answerRepository.findById(answerId).get();
            if (answer.getLikedNumber() > 0) {
                answer.setLikedNumber(answer.getLikedNumber() - 1);
                answerRepository.save(answer);
            } else {
                throw new BizException(ExceptionEnum.ANSWER_LIKED_NUMBER_IS_ZERO);
            }
        } else {
            throw new BizException(ExceptionEnum.ANSWER_NOT_EXIST);
        }

    }
}
