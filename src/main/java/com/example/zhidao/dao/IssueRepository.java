package com.example.zhidao.dao;

import com.example.zhidao.pojo.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue,Long> {
}
