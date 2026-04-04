package com.xixizai.personalblogwebsite.service.Impl;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.exception.*;
import com.xixizai.personalblogwebsite.mapper.RssLinkMapper;
import com.xixizai.personalblogwebsite.pojo.dto.RssLinkDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.RssLinkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

//订阅管理
@Service
public class RssLinkServiceImpl implements RssLinkService {

    @Resource
    private RssLinkMapper rssLinkMapper;

    @Override
    public Result getAllRssLinks() throws GetOptsException {
        try {
            List list = rssLinkMapper.getAllRssLinks();
            return Result.success(list);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new GetOptsException(MessageConstant.GET_OPERATIONS_FAILSURE);
        }
    }

    @Override
    public Result addRssLink(RssLinkDTO dto) throws AddOperationException {
        try {
            if (dto == null) {
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }
            rssLinkMapper.addRssLink(dto);
            return Result.success("添加成功");
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new AddOperationException(MessageConstant.ADD_OPERATION_FAILSURE);
        }
    }

    @Override
    public Result updateRssLink(RssLinkDTO dto) throws UpdateOperationsException {
        try {
            if (dto == null) {
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }
            if (dto.getId() == null) {
                throw new PassedParameterException(MessageConstant.ID_NOT_FOUND);
            }
            if (rssLinkMapper.findById(dto.getId()) == null) {
                return Result.error("此 ID 的数据不存在");
            }
            rssLinkMapper.updateRssLink(dto);
            return Result.success("更新成功");
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new UpdateOperationsException(MessageConstant.UPDATE_OPERATIONS_FAILSURE);
        }
    }

    @Override
    public Result batchDeleteRssLinks(List<Long> ids) throws BatchDeleteRssSubscriptionException {
        try {
            if (ids == null || ids.isEmpty()) {
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }

            List<Long> distinctIds = new ArrayList<>();
            for (Long id : ids) {
                if (!distinctIds.contains(id)) {
                    distinctIds.add(id);
                }
            }

            List<Long> updatedIds = new ArrayList<>();
            List<Long> nulledIds = new ArrayList<>();
            for (Long id : distinctIds) {
                if (rssLinkMapper.findById(id) == null) {
                    nulledIds.add(id);
                } else {
                    updatedIds.add(id);
                }
            }

            if (!updatedIds.isEmpty()) {
                rssLinkMapper.batchDeleteRssLinks(ids);
            }

            if (updatedIds.isEmpty()) {
                return Result.error("传入的 ID 列表中，没有任何一个存在：" + nulledIds);
            }
            if (nulledIds.isEmpty()) {
                return Result.success("批量删除成功，共删除 " + updatedIds.size() + " 个");
            }
            return Result.success("批量删除成功，成功删除 " + updatedIds.size() + " 个，不存在的 ID：" + nulledIds);

        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BatchDeleteRssSubscriptionException(MessageConstant.BATCH_DELETE_SKILLS_FAILSURE);
        }
    }
}
