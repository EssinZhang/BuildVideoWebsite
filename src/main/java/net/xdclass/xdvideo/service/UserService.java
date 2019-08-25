package net.xdclass.xdvideo.service;

import net.xdclass.xdvideo.domain.User;

/**
 * 用户业务接口
 */
public interface UserService {

    User saveWeChatUser(String code);
}
