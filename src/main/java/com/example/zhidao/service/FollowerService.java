package com.example.zhidao.service;

import com.example.zhidao.pojo.entity.User;

import java.util.List;

public interface FollowerService {
    void followUser(String myUsername, String followUsername);

    void unfollowUser(String myUsername, String followUsername);

    List<User> findFollowedUsers(String username, Integer page, Integer pageSize);
}
