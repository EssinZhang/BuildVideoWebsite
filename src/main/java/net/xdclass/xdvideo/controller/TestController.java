package net.xdclass.xdvideo.controller;

import net.xdclass.xdvideo.config.WeChatConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("test1")
    public String test(){
        System.out.println("xdclass.test");

        return "hello,xd video";
    }

    @Autowired
    private WeChatConfig weChatConfig;

    @RequestMapping("test_config")
    public String testConfig(){
        System.out.println(weChatConfig.getAppId());

        return weChatConfig.getAppId();
    }
}
