package com.xixizai.personalblogwebsite.controller.home;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.SystemConfigService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("homeSystemConfigController")
@RequestMapping("/home/systemConfig")
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
    public Result getSystemConfigByConfigKey(@PathVariable String configKey) throws GetOptsException {
        return systemConfigService.getSystemConfigByConfigKey(configKey);
    }

}
