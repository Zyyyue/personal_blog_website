package com.xixizai.personalblogwebsite.controller.admin;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.PassedParameterException;
import com.xixizai.personalblogwebsite.exception.UpdateOperationsException;
import com.xixizai.personalblogwebsite.pojo.dto.PersonalInfoDTO;
import com.xixizai.personalblogwebsite.pojo.entity.PersonalInfo;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.PersonalInforService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController("adminPersonalInforController")
@RequestMapping("/admin/personalInfo")
public class PersonalInforController {

    @Resource
    private PersonalInforService personalInforService;

    /**
     * 获取个人信息
     * @return
     * @throws GetOptsException
     */
    @GetMapping()
    public Result getPersonalInfor() throws GetOptsException {
        return personalInforService.getPersonalInfor();
    }

    /**
     * 更新个人信息
     * @param personalInfoDTO
     * @return
     * @throws PassedParameterException
     * @throws UpdateOperationsException
     */
    @PutMapping()
    public Result updatePersonalInfor(@RequestBody PersonalInfoDTO personalInfoDTO) throws PassedParameterException, UpdateOperationsException {
        return personalInforService.updatePersonalInfor(personalInfoDTO);
    }

}
