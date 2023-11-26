package com.example.zhidao.service;

import com.example.zhidao.pojo.entity.Follower;
import com.example.zhidao.pojo.entity.User;

import java.util.List;

public interface FollowerService {
    void followUser(Long followerId, Long followedId);
    void unfollowUser(Long followerId, Long followedId);
    List<User> findFollowedUsers(Long userId, Integer page, Integer pageSize);
}
