package com.example.zhidao.service.impl;

import com.example.zhidao.config.RedisConstantsConfig;
import com.example.zhidao.dao.*;
import com.example.zhidao.pojo.entity.*;
import com.example.zhidao.pojo.vo.common.BizException;
import com.example.zhidao.pojo.vo.common.ExceptionEnum;
import com.example.zhidao.service.AnswerService;
import com.example.zhidao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnswerServiceImpl implements AnswerService {
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private CollectAIAnswerRepository collectAIAnswerRepository;
    @Autowired
    private CollectAnswerRepository collectAnswerRepository;
    @Autowired
    private FollowerRepository followerRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisConstantsConfig redisConstantsConfig;
    @Autowired
    private IssueRepository issueRepository;

    @Override
    public List<Answer> findAnswerPages(Long issueId, Integer page, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC,
                "likedNumber"));
        return answerRepository.findByIssueId(issueId, pageRequest).getContent();
    }

    @Override
    @Transactional
    public Answer createAnswer(String username, Long issueId, String answerContent) {
        User user = userService.findUserByUsername(username);
        Issue issue = issueRepository.findById(issueId).get();
        issue.setAnswerNumber(issue.getAnswerNumber() + 1);
        issueRepository.save(issue);
        return answerRepository.save(Answer.builder().issueId(issueId).userId(user.getUserId())
                .answerContent(answerContent).likedNumber(0).commentNumber(0).collectNumber(0).build());
    }

    @Override
    @Transactional
    public void deleteAnswer(Long answerId) {
        if (answerRepository.findById(answerId).isPresent()) {
            Answer answer = answerRepository.findById(answerId).get();
            Issue issue = issueRepository.findById(answer.getIssueId()).get();
            issue.setAnswerNumber(issue.getAnswerNumber() - 1);
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

    @Transactional
    @Override
    public void collectAnswer(String username, Long answerId) {
        if (answerRepository.findById(answerId).isPresent()) {
            Answer answer = answerRepository.findById(answerId).get();
            answer.setCollectNumber(answer.getCollectNumber() + 1);
            answerRepository.save(answer);
            collectAnswerRepository.save(CollectAnswer.builder().answerId(answerId).userId(userService.findUserByUsername(username).getUserId()).build());
        } else {
            throw new BizException(ExceptionEnum.ANSWER_NOT_EXIST);
        }
    }

    @Transactional
    @Override
    public void unCollectAnswer(String username, Long answerId) {
        if (answerRepository.findById(answerId).isPresent()) {
            Answer answer = answerRepository.findById(answerId).get();
            if (answer.getCollectNumber() > 0) {
                answer.setCollectNumber(answer.getCollectNumber() - 1);
                answerRepository.save(answer);
                collectAnswerRepository.deleteByUserIdAndAnswerId(userService.findUserByUsername(username).getUserId(), answerId);
            } else {
                throw new BizException(ExceptionEnum.ANSWER_COLLECT_NUMBER_IS_ZERO);
            }
        } else {
            throw new BizException(ExceptionEnum.ANSWER_NOT_EXIST);
        }
    }

    @Override
    public List<Answer> findMyCollectedAnswer(String username, Integer page, Integer pageSize) {
        User user = userService.findUserByUsername(username);
        PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<CollectAnswer> collectedAnswers = collectAnswerRepository.findByUserId(user.getUserId(), pageRequest);

        List<Answer> answers = new ArrayList<>();
        for (CollectAnswer collectAnswer : collectedAnswers) {
            answers.add(answerRepository.findById(collectAnswer.getAnswerId()).orElseThrow(() ->
                    new BizException(ExceptionEnum.ANSWER_NOT_EXIST)));
        }
        return answers;
    }

    @Override
    public List<Answer> findMyAnswer(String username, Integer page, Integer pageSize) {
        User user = userService.findUserByUsername(username);
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));
        return answerRepository.findByUserId(user.getUserId(), pageable);
    }

    @Override
    public List<Object> findMyCollectedAIAndNormalAnswers(String username, Integer page, Integer pageSize) {
        User user = userService.findUserByUsername(username);
        PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));

        // 获取收藏的 AI 答案
        List<CollectAIAnswer> collectedAIAnswers = collectAIAnswerRepository.findAllByUserId(user.getUserId(),pageRequest);

        // 获取收藏的普通答案
        List<CollectAnswer> collectedAnswers = collectAnswerRepository.findByUserId(user.getUserId(), pageRequest);

        // 合并结果
        List<Object> combinedResults = new ArrayList<>();
        combinedResults.addAll(collectedAIAnswers);
        combinedResults.addAll(collectedAnswers);

        // 按收藏时间排序
        combinedResults.sort((o1, o2) -> {
            Date date1 = o1 instanceof CollectAIAnswer ? ((CollectAIAnswer) o1).getCreatedAt() : ((CollectAnswer) o1).getCreatedAt();
            Date date2 = o2 instanceof CollectAIAnswer ? ((CollectAIAnswer) o2).getCreatedAt() : ((CollectAnswer) o2).getCreatedAt();
            return date2.compareTo(date1); // 按照创建时间降序排序
        });

        // 返回正确的数据段
        return combinedResults.stream()
                .skip(page * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }
}
