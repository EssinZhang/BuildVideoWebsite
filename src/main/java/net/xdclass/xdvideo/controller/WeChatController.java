package net.xdclass.xdvideo.controller;

import net.xdclass.xdvideo.config.WeChatConfig;
import net.xdclass.xdvideo.domain.JsonData;
import net.xdclass.xdvideo.domain.User;
import net.xdclass.xdvideo.domain.VideoOrder;
import net.xdclass.xdvideo.service.UserService;
import net.xdclass.xdvideo.service.VideoOrderService;
import net.xdclass.xdvideo.utils.CommonUtils;
import net.xdclass.xdvideo.utils.JwtUtils;
import net.xdclass.xdvideo.utils.WeChatPayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;

@Controller
@RequestMapping("/api/v1/wechat")
public class WeChatController {

    @Autowired
    private WeChatConfig weChatConfig;

    @Autowired
    private UserService userService;

    @Autowired
    private VideoOrderService videoOrderService;

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


    /**
     * 微信扫码登录，回调地址
     * @param code
     * @param state
     * @param response
     * @throws IOException
     */
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

    /**
     * 微信支付回调
     */
    @RequestMapping ("/order/callback")
    public void weChatOrderCallBack(HttpServletRequest request,HttpServletResponse response) throws Exception {

        InputStream inputStream = request.getInputStream();
        //BufferedReader是包装设计模式，性能更高
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuffer stringBuffer = new StringBuffer();
        String line;
        while ( (line = bufferedReader.readLine()) != null){
            stringBuffer.append(line);
        }
        bufferedReader.close();
        inputStream.close();
        Map<String,String> callBackMap = WeChatPayUtils.xmlToMap(stringBuffer.toString());
        System.out.println(callBackMap.toString());

        SortedMap<String, String> sortedMap = CommonUtils.getSortedMap(callBackMap);

        //判断签名是否正确
        if (WeChatPayUtils.checkSign(sortedMap,weChatConfig.getKey())){

            if ("SUCCESS".equals(sortedMap.get("result_code") ) ){

                String outTradeNo = sortedMap.get("out_trade_no");
                VideoOrder orderByOutTradeNo = videoOrderService.findOrderByOutTradeNo(outTradeNo);

                if (orderByOutTradeNo.getState() == 0){
                    VideoOrder videoOrder = new VideoOrder();
                    videoOrder.setOpenid(sortedMap.get("openid"));
                    videoOrder.setOutTradeNo(outTradeNo);
                    videoOrder.setNotifyTime(new Date());
                    videoOrder.setState(1);

                    //更新订单状态
                    int rows = videoOrderService.updateVideoOrderByOutTradeNo(videoOrder);
                    System.out.println("更新结果row："+rows);
                    //影响行数判断
                    if (rows == 1 ){//订单处理成功
                        response.setContentType("text/xml");
                        response.getWriter().println("success");
                    }
                }
            }

        }
        //都处理失败
        response.setContentType("text/xml");
        response.getWriter().println("fail");


    }

}
