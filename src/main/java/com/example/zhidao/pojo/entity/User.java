package com.example.zhidao.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
public class User {
    @Id
    private Long userId;
    private String username;//用户名
    private String password;//密码
    private String nickName;//昵称
    private String profileImagePath;//头像
}
