package com.xixizai.personalblogwebsite.controller.admin;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.AddOperationException;
import com.xixizai.personalblogwebsite.exception.BatchDeleteExperienceException;
import com.xixizai.personalblogwebsite.exception.UpdateOperationsException;
import com.xixizai.personalblogwebsite.pojo.dto.ExperienceDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.ExperienceService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/experience")
public class ExperienceController {

    @Resource
    private ExperienceService experienceService;

    /**
     * 获取经历列表
     * @param types
     * @return
     * @throws GetOptsException
     */
    @GetMapping()
    public Result getExperienceList(@RequestParam List<Integer>types) throws GetOptsException {
        return experienceService.getExperienceList(types);
    }

    /**
     * 添加经历
     * @param experienceDTO
     * @return
     */
    @PostMapping()
    public Result addExperience(@RequestBody ExperienceDTO experienceDTO) throws AddOperationException {
        return experienceService.addExperience(experienceDTO);
    }

    /**
     * 更新经历
     * @param experienceDTO
     * @return
     * @throws UpdateOperationsException
     */
    @PutMapping()
    public Result updateExperience(@RequestBody ExperienceDTO experienceDTO) throws UpdateOperationsException {
        return experienceService.updateExperience(experienceDTO);
    }

    /**
     * 批量删除经历
     * @param ids
     * @return
     * @throws BatchDeleteExperienceException
     */
    @DeleteMapping()
    public Result batchDeleteExperience(@RequestParam List<Long>ids) throws BatchDeleteExperienceException {
        return experienceService.batchDeleteExperience(ids);
    }
}
