package com.example.zhidao.pojo.vo.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
public class SearchRequest {
    @NotBlank
    private String issueTitle;
    @NotNull
    private Integer page;
    @NotNull
    private Integer pageSize;
}
