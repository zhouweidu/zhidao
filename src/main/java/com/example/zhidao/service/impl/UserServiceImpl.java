package com.example.zhidao.service.impl;

import cn.hutool.json.JSONUtil;
import com.example.zhidao.config.RedisConstantsConfig;
import com.example.zhidao.dao.UserRepository;
import com.example.zhidao.pojo.entity.User;
import com.example.zhidao.pojo.vo.common.BizException;
import com.example.zhidao.pojo.vo.common.ExceptionEnum;
import com.example.zhidao.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisConstantsConfig redisConstantsConfig;

    @Override
    public User login(String username, String password) {
        User user = userRepository.findUserByUsernameAndPassword(username, DigestUtils.md5Hex(password));
        if (user != null) {
            return user;
        } else {
            throw new BizException(ExceptionEnum.INVALID_CREDENTIAL);

        }
    }

    @Override
    public User register(String username, String password, String nickName, String profileImagePath) {
        User user = userRepository.findUserByUsername(username);
        if (user != null) {
            throw new BizException(ExceptionEnum.USERNAME_EXIST);
        } else {
            return userRepository.save(User.builder().username(username).password(DigestUtils.md5Hex(password))
                    .nickName(nickName).profileImagePath(profileImagePath).build());
        }
    }

    @Transactional
    @Override
    public User editInfo(String username, String password, String nickName, String profileImagePath) {
        User user = userRepository.findUserByUsernameAndPassword(username, DigestUtils.md5Hex(password));
        if (user != null) {
            user.setNickName(nickName);
            user.setProfileImagePath(profileImagePath);
            User res = userRepository.save(user);
            stringRedisTemplate.delete(redisConstantsConfig.getUsernameKey() + username);
            return res;
        } else {
            throw new BizException(ExceptionEnum.INVALID_CREDENTIAL);
        }
    }

    @Transactional
    @Override
    public User modifyPassword(String username, String oldPassword, String newPassword) {
        User user = userRepository.findUserByUsernameAndPassword(username, DigestUtils.md5Hex(oldPassword));
        if (user != null) {
            user.setPassword(DigestUtils.md5Hex(newPassword));
            User res = userRepository.save(user);
            stringRedisTemplate.delete(redisConstantsConfig.getUsernameKey() + username);
            return res;
        } else {
            throw new BizException(ExceptionEnum.INVALID_CREDENTIAL);
        }
    }

    @Override
    public User findUserInfo(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            return userRepository.findById(userId).get();
        } else {
            throw new BizException(ExceptionEnum.USER_NOT_FOUND);
        }
    }

    @Transactional
    @Override
    public User findUserByUsername(String username) {
        String userJson = stringRedisTemplate.opsForValue().get(
                redisConstantsConfig.getUsernameKey() + username);
        if (userJson != null) {
            return JSONUtil.toBean(userJson, User.class);
        } else {
            User user = userRepository.findUserByUsername(username);
            if (user != null) {
                stringRedisTemplate.opsForValue().set(redisConstantsConfig.getUsernameKey() + username,
                        JSONUtil.toJsonStr(user), redisConstantsConfig.getUsernameTTL(), TimeUnit.MINUTES);
                return user;
            } else {
                throw new BizException(ExceptionEnum.USER_NOT_FOUND);
            }
        }
    }

}
