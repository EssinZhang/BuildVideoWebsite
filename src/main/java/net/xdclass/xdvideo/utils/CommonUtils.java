package net.xdclass.xdvideo.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * 常用工具类的封装
 */
public class CommonUtils {

    /**
     * 生成uuid
     * @return
     */
    public static String generateUUID(){
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);

        return uuid;
    }

    /**
     * md5常用工具类
     * @param data
     * @return
     */
    public static String MD5(String data){
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(data.getBytes("UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            for (byte item : bytes){
                stringBuilder.append(Integer.toHexString((item & 0xFF) | 0x100 ).substring(1,3) );
            }
            return stringBuilder.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
