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
    private String issueCreatedAt;//问题创建时间
    private Long aiAnswerId;
    private Integer aiId;
    private String aiAnswerContent;
    private String aiAnswerCreatedAt;//ai回答时间
}
