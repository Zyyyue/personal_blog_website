package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.dto.DailyViewCountDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ReportMapper {

    @Select("select date(view_time)as date,count(*) as count from views where date (view_time) between #{begin} and #{end} group by date order by date")
    List<DailyViewCountDTO> getViewCountList(LocalDate begin,LocalDate end);
}
