package com.xixizai.personalblogwebsite.controller.admin;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.MusicService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/music")
public class MusicController {

    @Resource
    private MusicService musicService;

    /**
     * 根据id查询音乐
     * @param id
     * @return
     * @throws GetOptsException
     */
    @GetMapping("/{id}")
    public Result getMusicById(@PathVariable Long id) throws GetOptsException {
        return musicService.getMusicById(id);
    }

}
