package com.example.zhidao.service;

import com.example.zhidao.pojo.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    boolean login(String username, String password);

    boolean register(String username, String password, String nickName, MultipartFile file);

    boolean editInfo(String username, String password, String nickName, MultipartFile file);
}
