package com.example.zhidao.service;

import com.example.zhidao.pojo.entity.Issue;

import java.util.List;

public interface SearchService {
    List<Issue> search(String keyword, Integer page, Integer pageSize);
}
