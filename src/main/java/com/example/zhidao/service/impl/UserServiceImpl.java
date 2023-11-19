package com.example.zhidao.service.impl;

import com.example.zhidao.dao.UserRepository;
import com.example.zhidao.pojo.entity.User;
import com.example.zhidao.pojo.vo.common.BizException;
import com.example.zhidao.pojo.vo.common.ExceptionEnum;
import com.example.zhidao.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

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

    @Override
    public User editInfo(String username, String password, String nickName, String profileImagePath) {
        User user = userRepository.findUserByUsernameAndPassword(username, DigestUtils.md5Hex(password));
        if (user != null) {
            user.setNickName(nickName);
            user.setProfileImagePath(profileImagePath);
            return userRepository.save(user);
        } else {
            throw new BizException(ExceptionEnum.INVALID_CREDENTIAL);
        }
    }

    @Override
    public User modifyPassword(String username, String oldPassword, String newPassword) {
        User user = userRepository.findUserByUsernameAndPassword(username, DigestUtils.md5Hex(oldPassword));
        if (user != null) {
            user.setPassword(DigestUtils.md5Hex(newPassword));
            return userRepository.save(user);
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

}
