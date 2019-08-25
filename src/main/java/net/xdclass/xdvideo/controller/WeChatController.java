package net.xdclass.xdvideo.controller;

import net.xdclass.xdvideo.config.WeChatConfig;
import net.xdclass.xdvideo.domain.JsonData;
import net.xdclass.xdvideo.domain.User;
import net.xdclass.xdvideo.service.UserService;
import net.xdclass.xdvideo.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequestMapping("/api/v1/wechat")
public class WeChatController {

    @Autowired
    private WeChatConfig weChatConfig;

    /**
     * 拼装扫一扫登录url
     * @return
     */
    @GetMapping("login_url")
    @ResponseBody
    public JsonData loginUrl(@RequestParam(value = "access_page",required = true)String accessPage) throws UnsupportedEncodingException {
        String redirectUrl = weChatConfig.getOpenRedirectUrl();//获取开放平台重定向地址

        String callBackUrl = URLEncoder.encode(redirectUrl,"GBK");//进行编码

        String qrcodeUrl = String.format(WeChatConfig.getOpenQrcodeUrl(),weChatConfig.getOpenAppid(),callBackUrl,accessPage);

        return JsonData.buildSuccess(qrcodeUrl);
    }

    @Autowired
    private UserService userService;

    @GetMapping("/user/callback")
    public void weChatUserLoginCallBack(@RequestParam(value = "code",required = true) String code, String state, HttpServletResponse response) throws IOException {

        User user = userService.saveWeChatUser(code);
        if (user != null){
            //生成
            String webToken = JwtUtils.geneJsonWebToken(user);
            //state 当前用户的页面地址 需要拼接 http:// 这样才可以跳转
            response.sendRedirect(state+"?token="+webToken+"&head_img="+user.getHeadImg()+"&name="+URLEncoder.encode(user.getName(),"UTF-8"));

        }

    }

}
