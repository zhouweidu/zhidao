package com.example.zhidao.service.impl;

import com.example.zhidao.dao.FollowerRepository;
import com.example.zhidao.dao.UserRepository;
import com.example.zhidao.pojo.entity.Follower;
import com.example.zhidao.pojo.entity.User;
import com.example.zhidao.pojo.vo.common.BizException;
import com.example.zhidao.pojo.vo.common.ExceptionEnum;
import com.example.zhidao.service.FollowerService;
import com.example.zhidao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class FollwerServiceImpl implements FollowerService {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowerRepository followerRepository;

    @Override
    public void followUser(String myUsername, String followUsername) {
        if (myUsername.equals(followUsername)) {
            throw new BizException(ExceptionEnum.CANNOT_FOLLOW_SELF);
        }
        User myUser = userService.findUserByUsername(myUsername);
        User followUser = userService.findUserByUsername(followUsername);
        // 检查关注关系是否已存在
        boolean relationshipExists = followerRepository
                .existsByMyIdAndFollowerId(myUser.getUserId(), followUser.getUserId());
        if (relationshipExists) {
            throw new BizException(ExceptionEnum.RELATIONSHIP_ALREADY_EXISTS);
        }
        followerRepository.save(Follower.builder().myId(myUser.getUserId())
                .followerId(followUser.getUserId()).build());
    }


    @Override
    @Transactional
    public void unfollowUser(String myUsername, String followUsername) {
        if (myUsername.equals(followUsername)) {
            throw new BizException(ExceptionEnum.CANNOT_UNFOLLOW_SELF);
        }
        User myUser = userService.findUserByUsername(myUsername);
        User followUser = userService.findUserByUsername(followUsername);

        // 检查关注关系是否存在
        boolean relationshipExists = followerRepository
                .existsByMyIdAndFollowerId(myUser.getUserId(), followUser.getUserId());
        if (!relationshipExists) {
            throw new BizException(ExceptionEnum.RELATIONSHIP_DOES_NOT_EXIST);
        }
        followerRepository.deleteByMyIdAndFollowerId(myUser.getUserId(), followUser.getUserId());
    }

    @Override
    public List<User> findFollowedUsers(String username, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));
        User myUser = userService.findUserByUsername(username);
        // 获取分页的关注用户列表
        List<Follower> followedUsersPage = followerRepository.findByMyId(myUser.getUserId(), pageable);
        ArrayList<User> users = new ArrayList<>();
        for (Follower follower : followedUsersPage) {
            users.add(userRepository.findById(follower.getFollowerId()).get());
        }
        // 根据ID列表获取用户详细信息
        return users;
    }
}

