package com.xixizai.personalblogwebsite.pojo.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminChangePasswordDTO implements Serializable {

    @NotBlank(message = "旧密码不能为空")
    @Pattern(regexp = "^\\S{5,16}$",message = "长度只能在5,16之间")
    private String oldPassword;

    @NotBlank(message = "新密码不能为空")
    @Pattern(regexp = "^\\S{5,16}$",message = "长度只能在5,16之间")
    private String newPassword;

    @NotBlank(message = "确认密码不能为空")
    @Pattern(regexp = "^\\S{5,16}$",message = "长度只能在5,16之间")
    private String confirmNewPassword;
}
