package com.example.zhidao.controller;

import com.example.zhidao.mapper.UserMapper;
import com.example.zhidao.pojo.entity.User;
import com.example.zhidao.pojo.vo.common.ResultResponse;
import com.example.zhidao.pojo.vo.follower.FindFollowedUsersPagesRequest;
import com.example.zhidao.pojo.vo.follower.FollowOrNotRequest;
import com.example.zhidao.pojo.vo.user.UserVO;
import com.example.zhidao.service.FollowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FollowerController {
    @Autowired
    private FollowerService followerService;

    @PostMapping("/follower")
    public ResultResponse followUser(@Valid FollowOrNotRequest request) {
        followerService.followUser(request.getMyUsername(), request.getFollowUsername());
        return ResultResponse.success();
    }

    @DeleteMapping("/follower")
    public ResultResponse unfollowUser(@Valid FollowOrNotRequest request) {
        followerService.unfollowUser(request.getMyUsername(), request.getFollowUsername());
        return ResultResponse.success();
    }

    @GetMapping("/follower")
    public ResultResponse findFollowedUsers(@Valid FindFollowedUsersPagesRequest request) {
        List<User> followedUsers = followerService.findFollowedUsers(request.getUsername(),
                request.getPage(), request.getPageSize());
        ArrayList<UserVO> userVOList = new ArrayList<>();
        for (User followedUser : followedUsers) {
            userVOList.add(UserMapper.INSTANCT.entity2VO(followedUser));
        }
        return ResultResponse.success(userVOList);
    }
}


