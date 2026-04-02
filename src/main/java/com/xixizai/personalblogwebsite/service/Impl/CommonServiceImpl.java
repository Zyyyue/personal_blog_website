package com.xixizai.personalblogwebsite.service.Impl;

import cn.hutool.core.util.RandomUtil;
import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.exception.PassedParameterException;
import com.xixizai.personalblogwebsite.mapper.CommonMapper;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.pojo.vo.CaptchaVO;
import com.xixizai.personalblogwebsite.service.CommonService;
import com.xixizai.personalblogwebsite.utils.AliOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private AliOssUtil aliOssUtil;

    @Resource
    private CommonMapper commonMapper;

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

    /**
     * 生成算术验证码
     * @return
     * @throws Exception
     */
    @Override
    public Result generateCode() throws Exception {
        try{
            //第一个数字
            int font;
            //第二个数字
            int back;
            //运算符号
            int operation;
            //规定运算符号
            Map<Integer,String>operationMap=new HashMap<>();
            operationMap.put(1,"+");
            operationMap.put(2,"-");
            operationMap.put(3,"*");
            //值
            int result = 1;

            while(true){

                font=RandomUtil.randomInt(1,10);
                back=RandomUtil.randomInt(1,10);
                operation=RandomUtil.randomInt(1,4);

                switch (operation){
                    //如果是+
                    case 1: result=font+back;break;
                    //如果是-
                    case 2: result=font-back;break;
                    //如果是*
                    case 3: result=font*back;break;
                }

                if(result<0){
                    continue;
                }else{
                    break;
                }

            }
            CaptchaVO captchaVO=new CaptchaVO();
            //获取id
            String captchaId = "captcha_" + System.currentTimeMillis() + "_" + RandomUtil.randomInt(1,1001);
            //获取问题
            String question=new String();
            String temp=operationMap.get(operation);
            question=font+" "+temp+" "+back+" "+"="+" "+"?";

            captchaVO=captchaVO.builder()
                    .captchaId(captchaId)
                    .question(question)
                    .result(result)
                    .build();
            return Result.success(captchaVO);
        }catch (Exception exception){
            exception.printStackTrace();
            throw new Exception("生成异常");
        }

    }


}
