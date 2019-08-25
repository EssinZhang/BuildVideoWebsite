package net.xdclass.xdvideo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.xdclass.xdvideo.domain.Video;
import net.xdclass.xdvideo.mapper.VideoMapper;
import net.xdclass.xdvideo.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * video接口
 */
@RestController
@RequestMapping("/api/v1/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    /**
     * 分页处理
     * @param page 当前页数 默认为1
     * @param size 每页显示几条
     * @return 查询结果
     */
    @GetMapping("page")
    public Object pageVideo(@RequestParam(value = "page",defaultValue = "1")int page,
                            @RequestParam(value = "size",defaultValue = "10")int size){
        PageHelper.startPage(page,size);

        List<Video> videoList = videoService.findAll();
        PageInfo<Video> pageInfo = new PageInfo<>(videoList);

        return pageInfo;
    }

    /**
     * 根据id查找视频信息
     * @param id
     * @return
     */
    @GetMapping("findById")
    public Object findById(@RequestParam(value = "video_id",required = true) int id){
        return videoService.findById(id);
    }


}
