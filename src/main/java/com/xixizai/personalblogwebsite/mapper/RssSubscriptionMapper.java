package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.entity.RssSubscriptions;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RssSubscriptionMapper {

    @Select("select * from rss_subscriptions where is_active=1")
    List<RssSubscriptions> getAllRssSubscription();
}
