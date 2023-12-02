package com.example.zhidao.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.example.zhidao.config.RedisConstantsConfig;
import com.example.zhidao.dao.AIAnswerCommentRepository;
import com.example.zhidao.dao.AIAnswerRepository;
import com.example.zhidao.dao.CollectAIAnswerRepository;
import com.example.zhidao.dao.LikeAIAnswerRepository;
import com.example.zhidao.pojo.entity.*;
import com.example.zhidao.pojo.vo.common.BizException;
import com.example.zhidao.pojo.vo.common.ExceptionEnum;
import com.example.zhidao.service.AIAnswerService;
import com.example.zhidao.service.UserService;
import com.example.zhidao.service.XfService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AIAnswerServiceImpl implements AIAnswerService {
    @Value("${ai.aiId}")
    private Integer aiId;
    @Autowired
    private XfService xfService;
    @Autowired
    private AIAnswerRepository aiAnswerRepository;
    @Autowired
    private CollectAIAnswerRepository collectAIAnswerRepository;
    @Autowired
    private AIAnswerCommentRepository aiAnswerCommentRepository;
    @Autowired
    private LikeAIAnswerRepository likeAIAnswerRepository;
    @Autowired
    private UserService userService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisConstantsConfig redisConstantsConfig;

    @Transactional
    @Override
    public AIAnswer createAIAnswer(Long issueId, String question) {
        String aiAnswerString = xfService.sendQuestionToXf(question);
        AIAnswer aiAnswer = aiAnswerRepository.save(AIAnswer.builder().aiId(aiId).issueId(issueId)
                .aiAnswerContent(aiAnswerString).likedNumber(0)
                .commentNumber(0).collectNumber(0).build());
        log.info("Create aiAnswer [{}]", aiAnswer.getAiAnswerId());
        stringRedisTemplate.opsForValue().set(redisConstantsConfig.getAiAnswerKey() + issueId,
                JSONUtil.toJsonStr(aiAnswer), redisConstantsConfig.getAiAnswerTTL(), TimeUnit.MINUTES);
        return aiAnswer;
    }

    @Transactional
    @Override
    public AIAnswer getAIAnswer(Long issueId) {
        String aiAnswerJson = stringRedisTemplate.opsForValue()
                .get(redisConstantsConfig.getAiAnswerKey() + issueId);
        if (StrUtil.isNotBlank(aiAnswerJson)) {
            AIAnswer aiAnswerRedis = JSONUtil.toBean(aiAnswerJson, AIAnswer.class);
            log.info("Get aiAnswer from redis [{}]", aiAnswerRedis.getAiAnswerId());
            return aiAnswerRedis;
        }
        AIAnswer aiAnswer = aiAnswerRepository.findAIAnswerByIssueId(issueId);
        if (aiAnswer == null) {
            throw new BizException(ExceptionEnum.ISSUE_NOT_EXIST);
        }
        String jsonStr = JSONUtil.toJsonStr(aiAnswer);
        stringRedisTemplate.opsForValue().set(redisConstantsConfig.getAiAnswerKey() + issueId, jsonStr
                , redisConstantsConfig.getAiAnswerTTL(), TimeUnit.MINUTES);
        log.info("Get aiAnswer form database [{}]", aiAnswer.getAiAnswerId());
        return aiAnswer;
    }

    @Transactional
    @Override
    public void likedAIAnswer(Long aiAnswerId,String username) {
        if (aiAnswerRepository.findById(aiAnswerId).isPresent()) {
            AIAnswer aiAnswer = aiAnswerRepository.findById(aiAnswerId).get();
            aiAnswer.setLikedNumber(aiAnswer.getLikedNumber() + 1);
            aiAnswerRepository.save(aiAnswer);
            likeAIAnswerRepository.save(LikeAIAnswer.builder().aiAnswerId(aiAnswerId)
                            .userId(userService.findUserByUsername(username).getUserId()).build());
            stringRedisTemplate.delete(redisConstantsConfig.getAiAnswerKey() + aiAnswer.getIssueId());
        } else {
            throw new BizException(ExceptionEnum.AI_ANSWER_NOT_EXIST);
        }
    }

    @Transactional
    @Override
    public void unlikedAIAnswer(Long aiAnswerId,String username) {
        if (aiAnswerRepository.findById(aiAnswerId).isPresent()) {
            AIAnswer aiAnswer = aiAnswerRepository.findById(aiAnswerId).get();
            if (aiAnswer.getLikedNumber() > 0) {
                aiAnswer.setLikedNumber(aiAnswer.getLikedNumber() - 1);
                aiAnswerRepository.save(aiAnswer);
                likeAIAnswerRepository.deleteByAiAnswerIdAndUserId(aiAnswerId,
                        userService.findUserByUsername(username).getUserId());
                stringRedisTemplate.delete(redisConstantsConfig.getAiAnswerKey() + aiAnswer.getIssueId());
            } else {
                throw new BizException(ExceptionEnum.AI_ANSWER_LIKED_NUMBER_IS_ZERO);
            }
        } else {
            throw new BizException(ExceptionEnum.AI_ANSWER_NOT_EXIST);
        }
    }

    @Transactional
    @Override
    public void collectAIAnswer(String username, Long aiAnswerId) {
        if (aiAnswerRepository.findById(aiAnswerId).isPresent()) {
            AIAnswer aiAnswer = aiAnswerRepository.findById(aiAnswerId).get();
            aiAnswer.setCollectNumber(aiAnswer.getCollectNumber() + 1);
            aiAnswerRepository.save(aiAnswer);
            collectAIAnswerRepository.save(CollectAIAnswer.builder().aiAnswerId(aiAnswerId)
                    .userId(userService.findUserByUsername(username).getUserId()).build());
            stringRedisTemplate.delete(redisConstantsConfig.getAiAnswerKey() + aiAnswer.getIssueId());
        } else {
            throw new BizException(ExceptionEnum.AI_ANSWER_NOT_EXIST);
        }
    }

    @Transactional
    @Override
    public void unCollectAIAnswer(String username, Long aiAnswerId) {
        if (aiAnswerRepository.findById(aiAnswerId).isPresent()) {
            AIAnswer aiAnswer = aiAnswerRepository.findById(aiAnswerId).get();
            if (aiAnswer.getCollectNumber() > 0) {
                aiAnswer.setCollectNumber(aiAnswer.getCollectNumber() - 1);
                aiAnswerRepository.save(aiAnswer);
                collectAIAnswerRepository.deleteByUserIdAndAiAnswerId(
                        userService.findUserByUsername(username).getUserId(), aiAnswerId);
                stringRedisTemplate.delete(redisConstantsConfig.getAiAnswerKey() + aiAnswer.getIssueId());
            } else {
                throw new BizException(ExceptionEnum.AI_ANSWER_COLLECT_NUMBER_IS_ZERO);
            }
        } else {
            throw new BizException(ExceptionEnum.AI_ANSWER_NOT_EXIST);
        }
    }

    @Override
    public void createAIAnswerComment(String username, Long aiAnswerId, String content) {
        if (aiAnswerRepository.findById(aiAnswerId).isPresent()) {
            AIAnswer aiAnswer = aiAnswerRepository.findById(aiAnswerId).get();
            aiAnswer.setCommentNumber(aiAnswer.getCommentNumber() + 1);
            aiAnswerRepository.save(aiAnswer);
            aiAnswerCommentRepository.save(AIAnswerComment.builder().aiAnswerId(aiAnswerId)
                    .userId(userService.findUserByUsername(username).getUserId()).content(content).build());
            stringRedisTemplate.delete(redisConstantsConfig.getAiAnswerKey() + aiAnswer.getIssueId());
        } else {
            throw new BizException(ExceptionEnum.AI_ANSWER_NOT_EXIST);
        }
    }

    @Override
    @Transactional
    public void deleteAIAnswerComment(String username, Long commentId) {
        if (aiAnswerCommentRepository.findById(commentId).isPresent()) {
            AIAnswerComment aiAnswerComment = aiAnswerCommentRepository.findById(commentId).get();
            AIAnswer aiAnswer = aiAnswerRepository.findById(aiAnswerComment.getAiAnswerId()).get();
            if (!aiAnswerComment.getUserId().equals(userService.findUserByUsername(username).getUserId())) {
                throw new BizException(ExceptionEnum.AI_ANSWER_COMMENT_NOT_BELONG_TO_USER);
            }
            if (aiAnswer.getCommentNumber() > 0) {
                aiAnswer.setCommentNumber(aiAnswer.getCommentNumber() - 1);
                aiAnswerRepository.save(aiAnswer);
                aiAnswerCommentRepository.deleteById(commentId);
                stringRedisTemplate.delete(redisConstantsConfig.getAiAnswerKey() + aiAnswer.getIssueId());
            } else {
                throw new BizException(ExceptionEnum.AI_ANSWER_COMMENT_NUMBER_IS_ZERO);
            }
        } else {
            throw new BizException(ExceptionEnum.AI_ANSWER_COMMENT_NOT_EXIST);
        }

    }

    @Override
    public List<AIAnswer> getMyCollectAIAnswer(String username, Integer page, Integer pageSize) {
        User user = userService.findUserByUsername(username);
        PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<CollectAIAnswer> collectAIAnswerList = collectAIAnswerRepository.findAllByUserId(user.getUserId(), pageRequest);
        List<AIAnswer> aiAnswerList = new ArrayList<>();
        if (collectAIAnswerList == null || collectAIAnswerList.size() == 0) {
            return aiAnswerList;
        }
        for (CollectAIAnswer collectAIAnswer : collectAIAnswerList) {
            aiAnswerList.add(aiAnswerRepository.findById(collectAIAnswer.getAiAnswerId()).get());
        }
        return aiAnswerList;
    }
}
