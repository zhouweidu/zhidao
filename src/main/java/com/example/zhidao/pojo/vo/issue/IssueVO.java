package com.example.zhidao.pojo.vo.issue;

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
public class IssueVO {
    private Long issueId;
    private Long userId;
    private String nickName;
    private String issueTitle;
    private String issueContent;
    private Integer concernedNumber;//关注人数
    private Integer answerNumber;//回答数
    private String createdAt;
    private List<String> issueImages;
    private Boolean isConcerned;
}
