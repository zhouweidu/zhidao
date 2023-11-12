package com.example.zhidao.pojo.vo.user;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class LoginRequest {
    @NotNull
    @Size(min = 4, max = 20, message = "用户名长度必须在 4-20 之间")
    @Pattern(regexp = "^[a-z\\d-_]*$", message = "用户名只能包含小写字母,数字,下划线和连字符")
    private String username;

    @NotNull
    @Size(min = 8, max = 56, message = "密码长度必须在 8-56 之间")
    @Pattern.List({
            @Pattern(regexp = "^[\\x21-\\x7e]*$", message = "密码只能包含字母,数字和符号"),
            @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "密码未达到复杂性要求:密码必须包含大小写字母和数字")
    })
    private String password;
}
