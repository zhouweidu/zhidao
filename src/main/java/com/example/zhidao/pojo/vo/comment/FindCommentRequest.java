package com.example.zhidao.pojo.vo.comment;

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
public class FindCommentRequest {
    @NotNull
    Long answerId;
    @NotNull
    Integer page;
    @NotNull
    Integer pageSize;
}
