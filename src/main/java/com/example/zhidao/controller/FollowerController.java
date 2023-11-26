package com.example.zhidao.controller;

import com.example.zhidao.pojo.entity.User;
import com.example.zhidao.pojo.vo.common.ResultResponse;
import com.example.zhidao.pojo.vo.follower.FindFollowedUsersPagesRequest;
import com.example.zhidao.pojo.vo.follower.FollowOrNotRequest;
import com.example.zhidao.service.FollowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FollowerController {
    @Autowired
    private FollowerService followerService;

    @PostMapping("/follower/follow")
    public ResultResponse followUser(@Valid @RequestBody FollowOrNotRequest request) {
            followerService.followUser(request.getFollowerId(), request.getFollowedId());
            return ResultResponse.success("Successfully followed the user.");
    }

    @PostMapping("/follower/unfollow")
    public ResultResponse unfollowUser(@Valid @RequestBody FollowOrNotRequest request) {
            followerService.unfollowUser(request.getFollowerId(), request.getFollowedId());
            return ResultResponse.success("Successfully unfollowed the user.");
    }
    @GetMapping("/follower/followed")
    public ResultResponse findFollowedUsers(@Valid @RequestBody FindFollowedUsersPagesRequest request) {
        List<User> followedUsers = followerService.findFollowedUsers(request.getUserId(), request.getPage(), request.getPageSize());
        return ResultResponse.success(followedUsers);
    }
}


