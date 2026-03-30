package com.xixizai.personalblogwebsite.service.Impl;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.exception.PassedParameterException;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.CommonService;
import com.xixizai.personalblogwebsite.utils.AliOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.UUID;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private AliOssUtil aliOssUtil;

    @Override
    public Result uploadFile(MultipartFile file) throws GetOptsException {
        try {

            if(file==null||file.isEmpty()){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }

            //获取文件名和后缀
            String fileName=file.getOriginalFilename();
            String extension=fileName.substring(fileName.lastIndexOf(".")+1);

            //生成uuid文件名
            String uuidFileName= UUID.randomUUID()+"."+extension;

            //获取字节数组
            byte[]bytes=file.getBytes();

            //上传到oss
            String fileUrl=aliOssUtil.upload(bytes,extension,uuidFileName);

            return Result.success(fileUrl);
        }catch (Exception exception){
            exception.printStackTrace();
            throw new GetOptsException(MessageConstant.GET_OPERATIONS_FAILSURE);

        }

    }


}
