package com.xixizai.personalblogwebsite.service;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.AddOperationException;
import com.xixizai.personalblogwebsite.pojo.dto.MusicDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import org.springframework.stereotype.Service;

@Service
public interface MusicService {

    //根据id查询音乐
    Result getMusicById(Long id) throws GetOptsException;

    //添加音乐
    Result addMusic(MusicDTO musicDTO) throws AddOperationException;
}
