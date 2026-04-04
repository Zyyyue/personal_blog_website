package com.xixizai.personalblogwebsite.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * RSS 链接 VO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RssLinkVO implements Serializable {

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
}
