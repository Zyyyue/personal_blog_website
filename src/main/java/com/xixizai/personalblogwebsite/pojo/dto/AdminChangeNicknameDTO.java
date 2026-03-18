package com.xixizai.personalblogwebsite.pojo.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminChangeNicknameDTO {

    @NotBlank(message = "昵称不能为空")
    @Pattern(regexp = "^\\S{1,30}$",message = "昵称只能在30字之内")
    private String nickname;
}
