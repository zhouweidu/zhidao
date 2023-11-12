package com.example.zhidao.dao;

import com.example.zhidao.pojo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByUsernameAndPassword(String username,String password);
    User findUserByUsername(String username);
}
