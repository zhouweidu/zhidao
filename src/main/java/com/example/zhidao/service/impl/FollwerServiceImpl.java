package com.example.zhidao.service.impl;

import com.example.zhidao.dao.FollowerRepository;
import com.example.zhidao.dao.UserRepository;
import com.example.zhidao.pojo.entity.User;
import com.example.zhidao.pojo.entity.Follower;
import com.example.zhidao.service.FollowerService;
import com.example.zhidao.pojo.vo.common.BizException;
import com.example.zhidao.pojo.vo.common.ExceptionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollwerServiceImpl implements FollowerService {

    // 现有的成员和方法...

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowerRepository followerRepository;

    @Transactional
    @Override
    public void followUser(Long followerId, Long followedId) {
        if (followerId.equals(followedId)) {
            throw new BizException(ExceptionEnum.CANNOT_FOLLOW_SELF);
        }

        userRepository.findById(followerId)
                .orElseThrow(() -> new BizException(ExceptionEnum.USER_NOT_FOUND));
        userRepository.findById(followedId)
                .orElseThrow(() -> new BizException(ExceptionEnum.USER_NOT_FOUND));

        // 检查关注关系是否已存在
        boolean relationshipExists = followerRepository
                .existsByFollowerIdAndFollowedId(followerId, followedId);
        if (relationshipExists) {
            throw new BizException(ExceptionEnum.RELATIONSHIP_ALREADY_EXISTS);
        }

        Follower follower = new Follower();
        follower.setFollowerId(followerId).setFollowedId(followedId);
        followerRepository.save(follower);
    }


    @Transactional
    @Override
    public void unfollowUser(Long followerId, Long followedId) {
        if (followerId.equals(followedId)) {
            throw new BizException(ExceptionEnum.CANNOT_UNFOLLOW_SELF);
        }

        userRepository.findById(followerId)
                .orElseThrow(() -> new BizException(ExceptionEnum.USER_NOT_FOUND));
        userRepository.findById(followedId)
                .orElseThrow(() -> new BizException(ExceptionEnum.USER_NOT_FOUND));

        // 检查关注关系是否存在
        boolean relationshipExists = followerRepository
                .existsByFollowerIdAndFollowedId(followerId, followedId);
        if (!relationshipExists) {
            throw new BizException(ExceptionEnum.RELATIONSHIP_DOES_NOT_EXIST);
        }

        followerRepository.deleteByFollowerIdAndFollowedId(followerId, followedId);
    }

    @Override
    public List<User> findFollowedUsers(Long userId, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));

        // 获取分页的关注用户列表
        List<Follower> followedUsersPage = followerRepository.findByFollowerId(userId);
        // 获取用户关注的所有人的ID
        List<Long> followedUserIds = followerRepository.findByFollowerId(userId)
                .stream()
                .map(Follower::getFollowedId)
                .collect(Collectors.toList());

        // 根据ID列表获取用户详细信息
        return userRepository.findAllByUserIdIn(followedUserIds,pageable);
    }
}

