package com.xixizai.personalblogwebsite.controller.blog;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.MusicService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("blogMusicController")
@RequestMapping("/blog/music")
public class MusicController {

    @Resource
    private MusicService musicService;

    /**
     * 获取可见音乐
     * @return
     * @throws GetOptsException
     */
    @GetMapping()
    public Result getMusic() throws GetOptsException {
        return musicService.getMusic();
    }

}
