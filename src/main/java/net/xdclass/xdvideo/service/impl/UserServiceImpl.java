package net.xdclass.xdvideo.service.impl;

import net.xdclass.xdvideo.config.WeChatConfig;
import net.xdclass.xdvideo.domain.User;
import net.xdclass.xdvideo.mapper.UserMapper;
import net.xdclass.xdvideo.service.UserService;
import net.xdclass.xdvideo.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private WeChatConfig weChatConfig;

    @Autowired
    private UserMapper userMapper;

    @Override
    public User saveWeChatUser(String code) {

        String accessTokenUrl = String.format(WeChatConfig.getOpenAccessTokenUrl(),weChatConfig.getOpenAppid(),weChatConfig.getOpenAppsecret(),code);

        //获取access_token
        Map<String,Object> baseMap = HttpUtils.doGet(accessTokenUrl);
        if (baseMap == null || baseMap.isEmpty()){return null;}
        String accessToken = (String) baseMap.get("access_token");
        String openID = (String) baseMap.get("openid");

        User existUser = userMapper.findByOpenId(openID);
        if (existUser != null){//已有就不再添加
            return existUser;
        }

        //获取用户信息
        String userInfo = String.format(WeChatConfig.getOpenUserInfoUrl(),accessToken,openID);
        //获取access_toke
        Map<String,Object> baseUserInfoMap = HttpUtils.doGet(userInfo);
        if (baseUserInfoMap == null || baseUserInfoMap.isEmpty()){return null;}
        String nickname = (String) baseUserInfoMap.get("nickname");

        Double sexTemp = (Double) baseUserInfoMap.get("sex");//double类型转换
        int sex = sexTemp.intValue();
        String headImageURL = (String) baseUserInfoMap.get("headimgurl");
        String province = (String) baseUserInfoMap.get("province");
        String city = (String) baseUserInfoMap.get("city");

        try {
            //解决乱码
            nickname = new String(nickname.getBytes("ISO-8859-1"), "UTF-8");
            province = new String(province.getBytes("ISO-8859-1"),"UTF-8");
            city = new String(city.getBytes("ISO-8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        User user = new User();
        user.setName(nickname);
        user.setSex(sex);
        user.setHeadImg(headImageURL);
        user.setCity(city);
        user.setOpenid(openID);
        user.setCreateTime(new Date());

        userMapper.saveUser(user);//添加新的用户

        return user;
    }
}
