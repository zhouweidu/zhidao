package com.example.zhidao.dao;

import com.example.zhidao.pojo.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByUsernameAndPassword(String username,String password);
    User findUserByUsername(String username);

    List<User> findAllByUserIdIn(List<Long> followedUserIds, Pageable pageable);
}
