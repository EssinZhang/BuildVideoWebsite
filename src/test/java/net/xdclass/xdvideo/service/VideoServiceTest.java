package net.xdclass.xdvideo.service;

import net.xdclass.xdvideo.domain.Video;
import net.xdclass.xdvideo.mapper.VideoMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * dao层断言测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class VideoServiceTest {

    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoMapper videoMapper;

    @Test
    public void findAll() {
        List<Video> videoList = videoService.findAll();
        assertNotNull(videoList);
        for (Video video : videoList){
            System.out.println(video.getTitle());
        }

    }

    @Test
    public void findById() {
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void save() {
    }
}