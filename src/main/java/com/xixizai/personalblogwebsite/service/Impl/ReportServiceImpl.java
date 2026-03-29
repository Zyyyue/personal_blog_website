package com.xixizai.personalblogwebsite.service.Impl;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.mapper.ReportMapper;
import com.xixizai.personalblogwebsite.pojo.dto.DailyViewCountDTO;
import com.xixizai.personalblogwebsite.pojo.dto.DailyVisitorCountDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.pojo.vo.ViewReportVO;
import com.xixizai.personalblogwebsite.pojo.vo.VisitorReportVO;
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

    /**
     * 浏览量统计
     * @param begin
     * @param end
     * @return
     * @throws GetOptsException
     */
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

    /**
     * 访客统计
     * @param begin
     * @param end
     * @return
     * @throws GetOptsException
     */
    @Override
    public Result getVisitorStatistics(LocalDate begin, LocalDate end) throws GetOptsException {
        try{
            List<LocalDate>dateList=dataUtil.getDateTimeList(begin,end);
            List<DailyVisitorCountDTO>dailyVisitorCountDTOList=reportMapper.getVisitorCountList(begin,end);

            //转成Map方便查找
            Map<LocalDate,Integer>dailyVisitorMap=dailyVisitorCountDTOList.stream()
                    .collect(Collectors.toMap(DailyVisitorCountDTO::getDate,DailyVisitorCountDTO::getCount));

            //补全日期,没有数据的日期补0
            List<String>dateStrList=new ArrayList<>();
            List<String>countOldStrList=new ArrayList<>();
            List<Integer>countList=new ArrayList<>();
            for (LocalDate localDate : dateList) {
                countList.add(dailyVisitorMap.getOrDefault(localDate,0));
                countOldStrList.add(dailyVisitorMap.getOrDefault(localDate,0).toString());
            }

            List<Integer>newCountList=new ArrayList<>();
            //计算累计访客
            for(int i=0;i<countList.size();i++){
                if(i==0){
                    newCountList.add(countList.get(i));
                }else{
                    newCountList.add(newCountList.get(i-1)+countList.get(i));
                }
            }
            List<String>countStrList=new ArrayList<>();
            //再把累计访客的这个新的列表转化成字符串
            int i=0;
            for (LocalDate localDate : dateList) {
                dateStrList.add(localDate.toString());
                countStrList.add(newCountList.get(i).toString());
                i++;
            }
            VisitorReportVO visitorReportVO=VisitorReportVO.builder()
                    .dateList(String.join(",",dateStrList))
                    .totalVisitorCountList(String.join(",",countStrList))
                    .newVisitorCountList(String.join(",",countOldStrList))
                    .build();
            return Result.success(visitorReportVO);

        }catch (Exception exception){
            exception.printStackTrace();
            throw new GetOptsException(MessageConstant.GET_OPERATIONS_FAILSURE);
        }
    }

}
