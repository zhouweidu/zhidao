package com.example.zhidao.pojo.vo.follower;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
public class FollowOrNotRequest {
    @NotNull(message = "Follower ID cannot be null")
    private Long followerId;

    @NotNull(message = "Followed ID cannot be null")
    private Long followedId;
}

