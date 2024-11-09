package com.practice.domain.query;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ValidQuery {
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Email(message = "邮箱格式不正确")
    private String email;

    @Size(min = 6, max = 20, message = "密码长度必须在6到20之间")
    private String password;
}