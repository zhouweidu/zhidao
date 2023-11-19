package com.example.zhidao.dao;

import com.example.zhidao.pojo.entity.IssueImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueImageRepository extends JpaRepository<IssueImage,String> {
    List<IssueImage> findIssueImagesByIssueId(Long issueId);
}
