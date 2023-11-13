package com.example.zhidao.pojo.vo.issue;

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
public class IssueVO {
    private String issueTitle;
    private String issueContent;
    private Integer concernedNumber;//关注人数
    private Integer answerNumber;//回答数
    private Date createdAt;//创建时间
}
