package com.xixizai.personalblogwebsite.controller.admin;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.AddOperationException;
import com.xixizai.personalblogwebsite.pojo.dto.MusicDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.MusicService;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 添加音乐
     * @param musicDTO
     * @return
     * @throws AddOperationException
     */
    @PostMapping()
    public Result addMusic(@RequestBody MusicDTO musicDTO) throws AddOperationException {
        return musicService.addMusic(musicDTO);
    }



}
