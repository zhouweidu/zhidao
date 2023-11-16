package com.example.zhidao.pojo.vo.comment;

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
public class CommentVO {
    private Long id;
    private Long userId;
    private Long answerId;
    private String content;
    private Integer likedNumber;
    private String createdAt;
}
