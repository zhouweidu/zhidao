package com.example.zhidao.pojo.vo.answer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
public class AnswerVO {
    private Long answerId;
    private Long issueId;
    private Long userId;
    private String answerContent;
    private Integer likedNumber;//点赞数
    private Integer commentNumber;//评论数
    private Integer collectNumber;//收藏数
    private String createdAt;
    private List<String> answerImages;
}
