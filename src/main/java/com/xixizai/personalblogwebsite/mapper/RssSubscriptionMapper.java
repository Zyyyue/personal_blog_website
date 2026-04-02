package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.dto.RssSubscriptionDTO;
import com.xixizai.personalblogwebsite.pojo.entity.RssSubscriptions;
import com.xixizai.personalblogwebsite.pojo.vo.RssSubscriptionStatusVO;
import org.apache.ibatis.annotations.Insert;
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

    //批量删除订阅
    void batchDeleteRssSubscriptions(List<Long> ids);

    //添加订阅
    @Insert("insert into rss_subscriptions (email,visitor_id,subscribe_time,nickname) values (#{email},#{visitorId},now(),#{nickname})")
    void addRssSubscription(RssSubscriptionDTO rssSubscriptionDTO);

    //根据visitorId查找
    @Select("select * from rss_subscriptions where visitor_id=#{visitorId}")
    RssSubscriptions findByVisitorId(Long visitorId);

    @Update("update rss_subscriptions set is_active=0,un_subscribe_time=now() where email=#{email}")
    void unSubscribe(String email);

    @Select("select * from rss_subscriptions where visitor_id=#{visitorId}")
    RssSubscriptionStatusVO getWeatherSubscribe(Long visitorId);
}
