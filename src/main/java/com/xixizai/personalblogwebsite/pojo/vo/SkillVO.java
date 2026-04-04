package com.xixizai.personalblogwebsite.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SkillVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    // 技能名称
    private String name;

    // 技能描述
    private String description;

    // 图标 url
    private String icon;

    // 熟练度，0-100
    private Integer level;

    // 排序，越小越靠前
    private Integer sort;

}
