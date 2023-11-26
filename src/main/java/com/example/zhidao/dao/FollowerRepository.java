package com.example.zhidao.dao;

import com.example.zhidao.pojo.entity.Follower;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowerRepository extends JpaRepository<Follower, Long> {
    // 根据关注者ID查找所有关注关系
    List<Follower> findByMyId(Long userId, Pageable pageable);

    // 删除特定的关注关系
    void deleteByMyIdAndFollowerId(Long myId, Long followerId);

    // 检查特定的关注关系是否存在
    boolean existsByMyIdAndFollowerId(Long myId, Long followerId);
}


