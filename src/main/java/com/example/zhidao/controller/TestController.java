package com.example.zhidao.controller;

import com.example.zhidao.dao.IssueRepository;
import com.example.zhidao.pojo.entity.Issue;
import com.example.zhidao.pojo.entity.IssueImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping
    public String test() {
        return "hello world!";
    }
}
