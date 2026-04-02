package com.xixizai.personalblogwebsite.service;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface CommonService {

    //上传文件
    Result uploadFile(MultipartFile file) throws GetOptsException;

    //生成算术验证码
    Result generateCode() throws Exception;
}
