package com.example.zhidao.service.impl;

import com.example.zhidao.dao.UserRepository;
import com.example.zhidao.pojo.entity.User;
import com.example.zhidao.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean login(String username, String password) {
        String md5Hex = DigestUtils.md5Hex(password);
        User user = userRepository.findUserByUsername(username);
        return user != null && user.getPassword().equals(md5Hex);
    }

    @Override
    public boolean register(String username, String password, String nickName, MultipartFile file) {
        return false;
    }

    @Override
    public boolean editInfo(String username, String password, String nickName, MultipartFile file) {
        return false;
    }
}
