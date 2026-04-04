package com.xixizai.personalblogwebsite.pojo.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 技能 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkillDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    // 技能名称
    @NotBlank(message = "技能名称不能为空")
    @Size(max = 20, message = "技能名称不能超过 20 字")
    private String name;

    // 技能描述
    @Size(max = 255, message = "技能描述不能超过 255 字")
    private String description;

    // 图标 url
    private String icon;

    // 熟练度，0-100
    private Integer level;

    // 排序，越小越靠前
    private Integer sort;

    // 是否可见
    private Integer isVisible;
}
