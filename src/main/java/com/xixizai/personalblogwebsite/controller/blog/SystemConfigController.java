package com.xixizai.personalblogwebsite.controller.blog;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.SystemConfigService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("blogSystemConfigController")
@RequestMapping("/blog/systemConfig")
public class SystemConfigController {

    @Resource
    private SystemConfigService systemConfigService;

    /**
     * 根据配置键获取配置
     * @param configKey
     * @return
     * @throws GetOptsException
     */
    @GetMapping("/key/{configKey}")
    public Result getSystemConfig(@PathVariable String configKey) throws GetOptsException {
        return systemConfigService.getSystemConfigByConfigKey(configKey);
    }

}
