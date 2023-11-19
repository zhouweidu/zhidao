package com.example.zhidao.controller;

import com.example.zhidao.mapper.UserMapper;
import com.example.zhidao.pojo.entity.User;
import com.example.zhidao.pojo.vo.common.ResultResponse;
import com.example.zhidao.pojo.vo.user.LoginRequest;
import com.example.zhidao.pojo.vo.user.ModifyPasswordRequest;
import com.example.zhidao.pojo.vo.user.RegisterOrEditInfoRequest;
import com.example.zhidao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user/{userId}")
    public ResultResponse getUserInfo(@PathVariable("userId") Long userId) {
        User user = userService.findUserInfo(userId);
        return ResultResponse.success(UserMapper.INSTANCT.entity2VO(user));
    }

    @PostMapping("/user/login")
    public ResultResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        User user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return ResultResponse.success(UserMapper.INSTANCT.entity2VO(user));
    }

    @PostMapping("/user")
    public ResultResponse register(@Valid @RequestBody RegisterOrEditInfoRequest registerRequest) {
        userService.register(registerRequest.getUsername(), registerRequest.getPassword(),
                registerRequest.getNickName(), registerRequest.getProfileImagePath());
        return ResultResponse.success();
    }

    @PutMapping("/user/info")
    public ResultResponse editInfo(@Valid @RequestBody RegisterOrEditInfoRequest editInfoRequest) {
        userService.editInfo(editInfoRequest.getUsername(), editInfoRequest.getPassword(),
                editInfoRequest.getNickName(), editInfoRequest.getProfileImagePath());
        return ResultResponse.success();
    }

    @PutMapping("/user/password")
    public ResultResponse modifyPassword(@Valid @RequestBody ModifyPasswordRequest modifyPasswordRequest) {
        userService.modifyPassword(modifyPasswordRequest.getUsername(),
                modifyPasswordRequest.getOldPassword(), modifyPasswordRequest.getNewPassword());
        return ResultResponse.success();
    }

}
