package com.xixizai.personalblogwebsite.service.Impl;

import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.exception.AddOperationException;
import com.xixizai.personalblogwebsite.exception.BatchDeleteViewRecordsException;
import com.xixizai.personalblogwebsite.exception.PassedParameterException;
import com.xixizai.personalblogwebsite.mapper.ViewMapper;
import com.xixizai.personalblogwebsite.pojo.entity.Views;
import com.xixizai.personalblogwebsite.pojo.entity.Visitors;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.ViewService;
import com.xixizai.personalblogwebsite.service.VisitorService;
import com.xixizai.personalblogwebsite.utils.IpUtil;
import com.xixizai.personalblogwebsite.utils.UserAgentUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ViewServiceImpl implements ViewService {


    @Resource
    @Lazy
    private ViewMapper viewMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private VisitorService visitorService;

    @Resource
    private UserAgentUtil userAgentUtil;

    @Value("${amap.api.key}")
    private String amapKey;

    /**
     * 批量删除浏览记录
     * @param ids
     * @return
     * @throws BatchDeleteViewRecordsException
     */
    @Override
    public Result batchDeleteViewRecords(List<Long> ids) throws BatchDeleteViewRecordsException {
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
                if(viewMapper.findById(id)==null){
                    nulledIds.add(id);
                }else{
                    updatedIds.add(id);
                }
            }

            //批量删除浏览记录
            if(!updatedIds.isEmpty()){
                viewMapper.batchDeleteViewRecords(ids);
            }

            //返回结果
            if(updatedIds.isEmpty()){
                return Result.error("传入的ID列表中，没有任何一个存在：" +nulledIds);
            }
            if(nulledIds.isEmpty()){
                return Result.success(("批量删除浏览记录成功，共浏览记录 " + updatedIds.size() + " 个浏览纪录"));
            }
            return Result.success("批量删除浏览记录成功，成功删除 " + updatedIds.size() + " 个浏览纪录，"
                    + "不存在的ID：" + nulledIds);

        }catch (Exception exception){
            exception.printStackTrace();
            throw new BatchDeleteViewRecordsException(MessageConstant.BATCH_DELETE_VIEW_RECORDS_FAILSURE);
        }
    }

    /**
     * 添加浏览记录
     * @param views
     * @throws AddOperationException
     */
    @Override
    public void addViewRecord(Views views, HttpServletRequest request) throws AddOperationException {
        try{

            //如果传进来的参数为空添加失败
            if(views==null){
                throw new AddOperationException(MessageConstant.ADD_OPERATION_FAILSURE);
            }

            //根据请求获取游客的id
            Long visitorIdByRequest = visitorService.getVisitorIdByRequest(request);
            if(visitorIdByRequest==null){
                //如果查找出来为空,说明这是一个新访客，需要创建
                Visitors visitor=new Visitors();
                String clientIp = IpUtil.getClientIp(request);
                if(IpUtil.isLocalIp(clientIp)){
                    //如果是本地ip,就转化,这里获取到了ip
                    clientIp = IpUtil.getLocalHostIp();
                }
                Map<String, String> geoInfo = IpUtil.getGeoInfo(clientIp);
                //获取国家省份城市
                String country = geoInfo.get("country");
                String province=geoInfo.get("province");
                String city=geoInfo.get("city");
                //获取sessionId
                String sessionId = request.getSession().getId();
                //获取用户代理
                String userAgentString = request.getHeader("User-Agent");
                String browserName = userAgentUtil.getBrowserName(userAgentString);
                String osName = userAgentUtil.getOsName(browserName);
                //获取经纬度

                Map<String, String> locationFromRequest = IpUtil.getGeoInfo(clientIp);
                String longitude = locationFromRequest.get("longitude");
                String latitude = locationFromRequest.get("latitude");


                visitor=visitor.builder()
                        .country(country)
                        .province(province)
                        .city(city)
                        .sessionId(sessionId)
                        .userAgent(osName)
                        .ip(clientIp)
                        .longitude(longitude)
                        .latitude(latitude)
                        .build();

                //添加访客
                visitorService.addVisitors(visitor,request);

                //获取访客从新建的visitor对象中获取
                visitorIdByRequest = visitor.getId();
            }else{
                //老访客调用 addVisitors增加total_views
                Visitors visitor = new Visitors();
                String clientIp = IpUtil.getClientIp(request);
                if(IpUtil.isLocalIp(clientIp)){
                    clientIp = IpUtil.getLocalHostIp();
                }
                visitor.setIp(clientIp);
                visitorService.addVisitors(visitor, request);
            }
            //获取访客Id
            views.setVisitorId(visitorIdByRequest);
            //获取用户代理
            String userAgentString = request.getHeader("User-Agent");
            String browserName = userAgentUtil.getBrowserName(userAgentString);
            String osName = userAgentUtil.getOsName(browserName);
            views.setUserAgent(osName);
            //获取ip地址
            String clientIp=IpUtil.getClientIp(request);
            if(IpUtil.isLocalIp(clientIp)){
                clientIp=IpUtil.getLocalHostIp();
            }
            views.setIpAddress(clientIp);
            log.info("view的pagePath={}",views.getPagePath());
            viewMapper.addViewRecord(views);

        }catch (Exception exception){
            exception.printStackTrace();
            throw new AddOperationException(MessageConstant.ADD_OPERATION_FAILSURE);
        }
    }


}
