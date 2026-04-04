package com.xixizai.personalblogwebsite.pojo.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * RSS 链接 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RssLinkDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    // 名称
    @NotBlank(message = "名称不能为空")
    @Size(max = 50, message = "名称不能超过 50 字")
    private String name;

    // RSS 地址
    @NotBlank(message = "RSS 地址不能为空")
    @Size(max = 500, message = "RSS 地址不能超过 500 字")
    private String url;

    // 是否启用，0-否，1-是
    private Integer isActive;

    // 排序，越小越靠前
    private Integer sort;
}
