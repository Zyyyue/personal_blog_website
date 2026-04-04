package com.xixizai.personalblogwebsite.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * RSS 链接配置
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RssLinks implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    // 名称
    private String name;

    // RSS 地址
    private String url;

    // 是否启用，0-否，1-是
    private Integer isActive;

    // 排序，越小越靠前
    private Integer sort;

    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    // 更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
