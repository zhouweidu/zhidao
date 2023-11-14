package com.example.zhidao.pojo.vo.issue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
public class IssueAndAIAnswerVO {
    private Long issueId;
    private String issueTitle;
    private String issueContent;
    private Integer concernedNumber;//关注人数
    private Integer answerNumber;//回答数
    private String issueCreatedAt;//问题创建时间
    private String aiAnswerContent;
    private Integer likedNumber;//点赞数
    private Integer commentNumber;//评论数
    private Integer collectNumber;//收藏数
    private String aiAnswerCreatedAt;//ai回答时间

}
