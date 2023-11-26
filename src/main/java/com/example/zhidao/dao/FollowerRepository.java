package com.example.zhidao.dao;

import com.example.zhidao.pojo.entity.Follower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowerRepository extends JpaRepository<Follower, Long> {
    // 根据关注者ID查找所有关注关系
    List<Follower> findByFollowerId(Long userId);

    // 根据被关注者ID查找所有关注关系
    List<Follower> findByFollowedId(Long followedId);

    // 删除特定的关注关系
    void deleteByFollowerIdAndFollowedId(Long userId, Long followedId);

    // 检查特定的关注关系是否存在
    boolean existsByFollowerIdAndFollowedId(Long userId, Long followedId);
}


