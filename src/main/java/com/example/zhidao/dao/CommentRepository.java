package com.example.zhidao.dao;

import com.example.zhidao.pojo.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
