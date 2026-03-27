package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.entity.RssSubscriptions;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface RssSubscriptionMapper {

    //获取所有订阅
    @Select("select * from rss_subscriptions where is_active=1")
    List<RssSubscriptions> getAllRssSubscription();

    //根据id查找订阅
    @Select("select * from rss_subscriptions where id=#{id}")
    RssSubscriptions findById(Long id);

    //更新订阅
    @Update("update rss_subscriptions set visitor_id=#{visitorId},nickname=#{nickname},email=#{email},is_active=#{isActive},subscribe_time=#{subscribeTime},un_subscribe_time=#{unSubscribeTime}  where  id=#{id}")
    void updateRssSubscription(RssSubscriptions rssSubscriptions);
}
