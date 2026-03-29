package com.xixizai.personalblogwebsite.controller.admin;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.ReportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;

@RestController
@RequestMapping("/admin/report")
public class ReportController {

    @Resource
    private ReportService reportService;

    /**
     * 浏览量统计
     * @param begin
     * @param end
     * @return
     * @throws GetOptsException
     */
    @GetMapping("/viewStatistics")
    public Result getViewStatistics(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) throws GetOptsException {
        return reportService.getViewStatistics(begin,end);
    }

    /**
     * 访客统计
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/visitorStatistics")
    public Result getVisitorStatistics(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) throws GetOptsException {
        return reportService.getVisitorStatistics(begin,end);
    }

    /**
     * 访客省份分布
     * @return
     * @throws GetOptsException
     */
    @GetMapping("/provinceDistribution")
    public Result getProvinceDistribution() throws GetOptsException {
        return reportService.getProvinceDistribution();
    }

    /**
     * 获取文章访问top10
     * @return
     * @throws GetOptsException
     */
    @GetMapping("/articleViewTop10")
    public Result getArticleViewTop10() throws GetOptsException {
        return reportService.getArticleViewTop10();
    }

    /**
     * 总览数据
     * @return
     * @throws GetOptsException
     */
    @GetMapping("/overview")
    public Result getAdminOverview() throws GetOptsException {
        return reportService.getAdminOverview();
    }
}
