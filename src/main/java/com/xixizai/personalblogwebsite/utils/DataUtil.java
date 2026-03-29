package com.xixizai.personalblogwebsite.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataUtil {

    //根据起始时间添加列表
    public List<LocalDate> getDateTimeList(LocalDate begin,LocalDate end){
        List<LocalDate> list=new ArrayList<>();
        LocalDate current=begin;
        while(!current.isAfter(end)){
            list.add(current);
            current=current.plusDays(1);
        }
        return list;
    }


}
