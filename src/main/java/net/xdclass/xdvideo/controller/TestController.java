package net.xdclass.xdvideo.controller;

import net.xdclass.xdvideo.config.WeChatConfig;
import net.xdclass.xdvideo.domain.JsonData;
import net.xdclass.xdvideo.domain.User;
import net.xdclass.xdvideo.domain.Video;
import net.xdclass.xdvideo.mapper.VideoMapper;
import net.xdclass.xdvideo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private WeChatConfig weChatConfig;

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private UserService userService;

    @RequestMapping("test1")
    public String test(){
        System.out.println("xdclass.test");

        return "hello,xd video";
    }

    @RequestMapping("test_config")
    public JsonData testConfig(){
        System.out.println(weChatConfig.getAppId());

        return JsonData.buildSuccess(weChatConfig.getAppId());
    }

    @GetMapping("testDB")
    public Object testDB(){
        return videoMapper.findAll();
    }

    @GetMapping("testLogin")
    public JsonData testLogin(){
        User userById = userService.findUserById(3);

        return JsonData.buildSuccess(userById);
    }
}
