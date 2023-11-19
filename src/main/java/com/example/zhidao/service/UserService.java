package com.example.zhidao.service;

import com.example.zhidao.pojo.entity.User;

public interface UserService {
    User login(String username, String password);

    User register(String username, String password, String nickName, String profileImagePath);

    User editInfo(String username, String password, String nickName, String profileImagePath);

    User modifyPassword(String username, String oldPassword, String newPassword);

    User findUserInfo(Long userId);

}
