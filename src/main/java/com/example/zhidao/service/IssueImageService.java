package com.example.zhidao.service;

import com.example.zhidao.pojo.entity.IssueImage;

import java.util.List;

public interface IssueImageService {
    List<IssueImage> createIssueImages(Long issueId, List<String> issueImages);
    List<IssueImage> findIssueImagesByIssueId(Long issueId);
}
