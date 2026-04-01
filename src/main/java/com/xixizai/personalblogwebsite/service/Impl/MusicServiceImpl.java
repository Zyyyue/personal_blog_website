package com.xixizai.personalblogwebsite.service.Impl;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.exception.*;
import com.xixizai.personalblogwebsite.mapper.MusicMapper;
import com.xixizai.personalblogwebsite.pojo.dto.MusicDTO;
import com.xixizai.personalblogwebsite.pojo.entity.Music;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.pojo.vo.MusicVO;
import com.xixizai.personalblogwebsite.service.MusicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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


    /**
     * 添加音乐
     * @param musicDTO
     * @return
     * @throws AddOperationException
     */
    @Override
    public Result addMusic(MusicDTO musicDTO) throws AddOperationException {

        try{

            if(musicDTO==null){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }

            musicMapper.addMusic(musicDTO);
            return Result.success("添加成功");
        }catch (Exception exception){
            exception.printStackTrace();
            throw new AddOperationException(MessageConstant.ADD_OPERATION_FAILSURE);
        }

    }

    /**
     * 更新音乐
     * @param musicDTO
     * @return
     * @throws UpdateOperationsException
     */
    @Override
    public Result updateMusic(MusicDTO musicDTO) throws UpdateOperationsException {
        try{

            if(musicDTO==null){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }

            if(musicDTO.getId()==null){
                throw new IdNotFoundException(MessageConstant.ID_NOT_FOUND);
            }

            musicMapper.updateMusic(musicDTO);
            return Result.success("更新成功");
        }catch (Exception exception){
            exception.printStackTrace();
            throw new UpdateOperationsException(MessageConstant.UPDATE_OPERATIONS_FAILSURE);
        }

    }

    @Override
    public Result batchDeleteMusics(List<Long> ids) throws BatchDeleteMusicsException {
        try {

            //判空
            if(ids==null||ids.isEmpty()){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }

            //去重一下id
            List<Long>distinctIds=new ArrayList<>();
            for (Long id : ids) {
                if(!distinctIds.contains(id)){
                    distinctIds.add(id);
                }
            }

            //再看一下数据库中是否有对应id

            //数据库中存在id的集合是updatedIds
            List<Long>updatedIds=new ArrayList<>();
            //数据库中不存在id的集合是nulledIds
            List<Long>nulledIds=new ArrayList<>();
            //再判断一下ids中的id是否都在数据库中存在,如果不存在的话就需要提示一下，然后删除已经存在的
            for (Long id : distinctIds) {
                if(musicMapper.getById(id)==null){
                    nulledIds.add(id);
                }else{
                    updatedIds.add(id);
                }
            }

            //批量删除音乐
            if(!updatedIds.isEmpty()){
                musicMapper.batchDeleteMusics(ids);
            }

            //返回结果
            if(updatedIds.isEmpty()){
                return Result.error("传入的ID列表中，没有任何一个存在：" +nulledIds);
            }
            if(nulledIds.isEmpty()){
                return Result.success(("批量删除成功，共审核 " + updatedIds.size() + " 首音乐"));
            }
            return Result.success("批量删除成功，成功删除 " + updatedIds.size() + " 首，"
                    + "不存在的ID：" + nulledIds);

        }catch (Exception exception){
            exception.printStackTrace();
            throw new BatchDeleteMusicsException(MessageConstant.BATCH_DELETE_MUSICS_FAILSURE);
        }
    }


    /**
     * 获取可见音乐
     * @return
     * @throws GetOptsException
     */
    @Override
    public Result getMusic() throws GetOptsException {
       try{

           List<MusicVO>list=musicMapper.getMusic();
            return Result.success(list);

       }catch (Exception exception){
           exception.printStackTrace();
           throw new GetOptsException(MessageConstant.GET_OPERATIONS_FAILSURE);
       }

    }


}
