package com.xixizai.personalblogwebsite.controller.admin;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.AddOperationException;
import com.xixizai.personalblogwebsite.exception.UpdateOperationsException;
import com.xixizai.personalblogwebsite.pojo.dto.SkillDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.SkillService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/skill")
public class SkillController {

    @Resource
    private SkillService skillService;

    /**
     * 获取所有技能
     * @return
     * @throws GetOptsException
     */
    @GetMapping()
    public Result getAllSkills() throws GetOptsException {
        return skillService.getAllSkills();
    }

    /**
     * 添加技能
     * @param skillDTO
     * @return
     * @throws AddOperationException
     */
    @PostMapping()
    public Result addSkill(@RequestBody SkillDTO skillDTO) throws AddOperationException {
        return skillService.addSkill(skillDTO);
    }

    /**
     * 更新技能
     * @param skillDTO
     * @return
     * @throws UpdateOperationsException
     */
    @PutMapping()
    public Result updateSkill(@RequestBody SkillDTO skillDTO) throws UpdateOperationsException {
        return skillService.updateSkill(skillDTO);
    }


}
