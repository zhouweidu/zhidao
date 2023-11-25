package com.example.zhidao.service;

import com.example.zhidao.pojo.entity.Issue;

import java.util.List;

public interface SearchService {
    List<Issue> search(String issueTitle, Integer page, Integer pageSize);
}
