package com.xixizai.personalblogwebsite.service.Impl;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.exception.PassedParameterException;
import com.xixizai.personalblogwebsite.mapper.MusicMapper;
import com.xixizai.personalblogwebsite.pojo.entity.Music;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.MusicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MusicServiceImpl implements MusicService {


    @Resource
    private MusicMapper musicMapper;

    /**
     * 根据id查找音乐
     * @param id
     * @return
     * @throws GetOptsException
     */
    @Override
    public Result getMusicById(Long id) throws GetOptsException {

        try{

            if(id==null){
                throw new PassedParameterException(MessageConstant.ID_NOT_FOUND);
            }

            Music music=musicMapper.getById(id);
            return Result.success(music);
        }catch (Exception exception){
            exception.printStackTrace();
            throw new GetOptsException(MessageConstant.GET_OPERATIONS_FAILSURE);
        }

    }


}
