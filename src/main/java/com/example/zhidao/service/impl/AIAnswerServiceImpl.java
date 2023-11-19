package com.example.zhidao.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.example.zhidao.config.RedisConstantsConfig;
import com.example.zhidao.dao.AIAnswerRepository;
import com.example.zhidao.pojo.entity.AIAnswer;
import com.example.zhidao.pojo.vo.common.BizException;
import com.example.zhidao.pojo.vo.common.ExceptionEnum;
import com.example.zhidao.service.AIAnswerService;
import com.example.zhidao.service.XfService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisConstantsConfig redisConstantsConfig;

    @Override
    public AIAnswer createAIAnswer(Long issueId, String question) {
        String aiAnswerString = xfService.sendQuestionToXf(question);
        AIAnswer aiAnswer = aiAnswerRepository.save(AIAnswer.builder().aiId(aiId).issueId(issueId)
                .aiAnswerContent(aiAnswerString).likedNumber(0)
                .commentNumber(0).collectNumber(0).build());
        log.info("Create aiAnswer [{}]", aiAnswer);
        stringRedisTemplate.opsForValue().set(redisConstantsConfig.getAiAnswerKey() + issueId,
                JSONUtil.toJsonStr(aiAnswer), redisConstantsConfig.getAiAnswerTTL(), TimeUnit.MINUTES);
        return aiAnswer;
    }

    @Override
    public AIAnswer getAIAnswer(Long issueId) {
        String aiAnswerJson = stringRedisTemplate.opsForValue()
                .get(redisConstantsConfig.getAiAnswerKey() + issueId);
        if (StrUtil.isNotBlank(aiAnswerJson)) {
            AIAnswer aiAnswerRedis = JSONUtil.toBean(aiAnswerJson, AIAnswer.class);
            log.info("Get aiAnswer from cache [{}]", aiAnswerRedis);
            return aiAnswerRedis;
        }
        AIAnswer aiAnswer = aiAnswerRepository.findAIAnswerByIssueId(issueId);
        if (aiAnswer == null) {
            throw new BizException(ExceptionEnum.ISSUE_NOT_EXIST);
        }
        String jsonStr = JSONUtil.toJsonStr(aiAnswer);
        stringRedisTemplate.opsForValue().set(redisConstantsConfig.getAiAnswerKey() + issueId, jsonStr
                , redisConstantsConfig.getAiAnswerTTL(), TimeUnit.MINUTES);
        log.info("Get aiAnswer form db [{}]", aiAnswer);
        return aiAnswer;
    }
}
