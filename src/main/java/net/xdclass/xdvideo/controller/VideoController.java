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
 * video�ӿ�
 */
@RestController
@RequestMapping("/api/v1/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    /**
     * ��ҳ����
     * @param page ��ǰҳ�� Ĭ��Ϊ1
     * @param size ÿҳ��ʾ����
     * @return ��ѯ���
     */
    @GetMapping("page")
    public Object pageVideo(@RequestParam(value = "page",defaultValue = "1")int page,
                            @RequestParam(value = "size",defaultValue = "10")int size){
        PageHelper.startPage(page,size);

        List<Video> videoList = videoService.findAll();
        PageInfo<Video> pageInfo = new PageInfo<>(videoList);
        Map<String,Object> data = new HashMap<>();
        data.put("total_size",pageInfo.getTotal());//������
        data.put("total_page",pageInfo.getPages());//��ҳ��
        data.put("current_page",page);//��ǰҳ
        data.put("data",pageInfo.getList());//����

        return data;
    }

    /**
     * ����id������Ƶ��Ϣ
     * @param id
     * @return
     */
    @GetMapping("findById")
    public Object findById(@RequestParam(value = "video_id",required = true) int id){
        return videoService.findById(id);
    }


}
