package com.xixizai.personalblogwebsite.service;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.AddOperationException;
import com.xixizai.personalblogwebsite.exception.BatchDeleteMusicsException;
import com.xixizai.personalblogwebsite.exception.UpdateOperationsException;
import com.xixizai.personalblogwebsite.pojo.dto.MusicDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MusicService {

    //根据id查询音乐
    Result getMusicById(Long id) throws GetOptsException;

    //添加音乐
    Result addMusic(MusicDTO musicDTO) throws AddOperationException;

    //更新音乐
    Result updateMusic(MusicDTO musicDTO) throws UpdateOperationsException;

    //批量删除音乐
    Result batchDeleteMusics(List<Long> ids) throws BatchDeleteMusicsException;
}
