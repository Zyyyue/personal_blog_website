package com.xixizai.personalblogwebsite.controller.admin;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.AddOperationException;
import com.xixizai.personalblogwebsite.exception.BatchDeleteMusicsException;
import com.xixizai.personalblogwebsite.exception.UpdateOperationsException;
import com.xixizai.personalblogwebsite.pojo.dto.MusicDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.MusicService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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


    /**
     * 更新音乐
     * @param musicDTO
     * @return
     * @throws UpdateOperationsException
     */
    @PutMapping()
    public Result updateMusic(@RequestBody MusicDTO musicDTO) throws UpdateOperationsException {
        return musicService.updateMusic(musicDTO);
    }

    /**
     * 批量删除音乐
     * @param ids
     * @return
     * @throws BatchDeleteMusicsException
     */
    @DeleteMapping()
    public Result batchDeleteMusics(@RequestParam List<Long>ids) throws BatchDeleteMusicsException {
        return musicService.batchDeleteMusics(ids);
    }


    /**
     * 分页查询音乐列表
     * @param page
     * @param pageSize

     * @return
     */
    @GetMapping("/page")
    public Result pageQueryMusic(@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "10") Integer pageSize) throws GetOptsException {
        return musicService.pageQueryMusic(page,pageSize);
    }

}
