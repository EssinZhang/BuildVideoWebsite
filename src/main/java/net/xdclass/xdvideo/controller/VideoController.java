package net.xdclass.xdvideo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.xdclass.xdvideo.domain.Video;
import net.xdclass.xdvideo.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String,Object> data = new HashMap<>();
        data.put("total_size",pageInfo.getTotal());//总条数
        data.put("total_page",pageInfo.getPages());//总页数
        data.put("current_page",page);//当前页
        data.put("data",pageInfo.getList());//数据

        return data;
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
