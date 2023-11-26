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
public class FindFollowedUsersPagesRequest {
    @NotNull
    private Long userId; // 用户ID

    @NotNull
    private Integer page; // 页码

    @NotNull
    private Integer pageSize; // 每页显示的数量
}
