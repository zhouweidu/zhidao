package com.example.zhidao.pojo.vo.aianswer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
public class AIAnswerVO {
    private Long aiAnswerId;
    private Integer aiId;
    private Long issueId;
    private String aiAnswerContent;
    private Integer likedNumber;//点赞数
    private Integer commentNumber;//评论数
    private Integer collectNumber;//收藏数
    private String createdAt;
}
