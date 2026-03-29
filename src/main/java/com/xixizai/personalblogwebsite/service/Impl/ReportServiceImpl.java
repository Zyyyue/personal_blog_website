package com.xixizai.personalblogwebsite.service.Impl;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.mapper.ReportMapper;
import com.xixizai.personalblogwebsite.pojo.dto.DailyViewCountDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.pojo.vo.ViewReportVO;
import com.xixizai.personalblogwebsite.service.ReportService;
import com.xixizai.personalblogwebsite.utils.DataUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Resource
    private ReportMapper reportMapper;

    @Resource
    private DataUtil dataUtil;

    @Override
    public Result getViewStatistics(LocalDate begin, LocalDate end) throws GetOptsException {
        try{

            List<LocalDate> dateTimeList = dataUtil.getDateTimeList(begin, end);
            List<DailyViewCountDTO>dailyViewCountDTOList=reportMapper.getViewCountList(begin,end);

            // 转为 Map 方便查找
            Map<LocalDate, Integer> viewMap = dailyViewCountDTOList.stream()
                    .collect(Collectors.toMap(DailyViewCountDTO::getDate, DailyViewCountDTO::getCount));

            // 补全日期，没有数据的日期补 0
            List<String> dateStrList = new ArrayList<>();
            List<String> countStrList = new ArrayList<>();
            for (LocalDate date : dateTimeList) {
                dateStrList.add(date.toString());
                countStrList.add(String.valueOf(viewMap.getOrDefault(date, 0)));
            }

            ViewReportVO viewReportVO = ViewReportVO.builder()
                    .dateList(String.join(",", dateStrList))
                    .viewCountList(String.join(",", countStrList))
                    .build();
            return Result.success(viewReportVO);


        }catch (Exception exception){
            exception.printStackTrace();;
            throw new GetOptsException(MessageConstant.GET_OPERATIONS_FAILSURE);
        }

    }
}
