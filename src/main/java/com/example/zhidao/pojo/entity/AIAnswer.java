package com.example.zhidao.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
public class AIAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aiAnswerId;
    private Integer aiId;
    private Long issueId;
    @Column(length = 4000)
    private String aiAnswerContent;
    private Integer likedNumber;//点赞数
    private Integer commentNumber;//评论数
    private Integer collectNumber;//收藏数
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;
}
