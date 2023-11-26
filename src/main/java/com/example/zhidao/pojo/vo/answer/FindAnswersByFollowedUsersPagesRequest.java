package com.example.zhidao.pojo.vo.answer;

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
public class FindAnswersByFollowedUsersPagesRequest {
    private Long userId;
    private Integer page;
    private Integer pageSize;
}

