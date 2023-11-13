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
    private Long aiId;
    private Long issueId;
    @Column(length = 4000)
    private String aiAnswerContent;
    private Long likedNumber;//点赞数
    private Long commentNumber;//评论数
    private Long collectNumber;//收藏数
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;
}
