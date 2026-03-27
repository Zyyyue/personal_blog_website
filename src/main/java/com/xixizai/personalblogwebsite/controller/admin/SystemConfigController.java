package com.xixizai.personalblogwebsite.controller.admin;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.AddOperationException;
import com.xixizai.personalblogwebsite.pojo.entity.SystemConfig;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.SystemConfigService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/systemConfig")
public class SystemConfigController {

    @Resource
    private SystemConfigService systemConfigService;

    /**
     * 获取所有系统配置
     * @return
     * @throws GetOptsException
     */
    @GetMapping()
    public Result getAllSystemConfigs() throws GetOptsException {
        return systemConfigService.getAllSystemConfigs();
    }

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

    /**
     * 根据id获取配置
     * @param id
     * @return
     * @throws GetOptsException
     */
    @GetMapping("/{id}")
    public Result getSystemConfigById(@PathVariable Long id) throws GetOptsException {
        return systemConfigService.getSystemConfigById(id);
    }

    /**
     * 添加配置
     * @param systemConfig
     * @return
     * @throws AddOperationException
     */
    @PostMapping()
    public Result addSystemConfig(@RequestBody SystemConfig systemConfig) throws AddOperationException {
        return systemConfigService.addSystemConfig(systemConfig);
    }
}
