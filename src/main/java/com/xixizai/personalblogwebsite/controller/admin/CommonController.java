package com.xixizai.personalblogwebsite.controller.admin;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.CommonService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController("adminCommonController")
@RequestMapping("/admin/upload")
public class CommonController {

    @Resource
    private CommonService commonService;

    /**
     * 上传文件
     * @param file
     * @return
     * @throws GetOptsException
     */
    @PostMapping("/upload")
    public Result uploadFile(@RequestParam MultipartFile file) throws GetOptsException {
        return commonService.uploadFile(file);
    }

}
