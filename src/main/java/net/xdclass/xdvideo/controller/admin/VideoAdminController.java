package net.xdclass.xdvideo.controller.admin;

import net.xdclass.xdvideo.domain.Video;
import net.xdclass.xdvideo.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/api/v1/video")
public class VideoAdminController {

    @Autowired
    private VideoService videoService;

    /**
     * 根据id删除视频
     * @param id
     * @return
     */
    @DeleteMapping("delById")
    public int delById(@RequestParam(value = "video_id",required = true)int id){
        return videoService.delete(id);
    }

    /**
     * 根据id更新对应的视频信息
     * @param id
     * @param title
     * @return
     */
    @PutMapping("updateVideo")
    public int update(@RequestBody Video video){

        return videoService.update(video);
    }

    /**
     * 保存视频对象
     * @param title
     * @return
     */
    @PostMapping("insertVideo")
    public int insertVideo(@RequestBody Video video){

        return videoService.save(video);
    }

}
