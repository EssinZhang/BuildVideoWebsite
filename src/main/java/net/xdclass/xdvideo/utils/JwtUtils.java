package net.xdclass.xdvideo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.xdclass.xdvideo.domain.User;

import java.util.Date;

/**
 * jwt工具类·
 */
public class JwtUtils {

    public static final String SUBJECT = "xdclasses";

    public static final long EXPIRE = 1000 * 60 * 60 * 24 * 7;//过期时间设置为一周

    public static final String APPSECRET = "xd666";

    /**
     * 生成jwt的方法
     * @param user
     * @return
     */
    public static String geneJsonWebToken(User user){

        if (user == null || user.getId() == null || user.getName() == null
            || user.getHeadImg() == null){
            return null;
        }
        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("id", user.getId())
                .claim("name", user.getName())
                .claim("img", user.getHeadImg())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256, APPSECRET)
                .compact();

        return token;
    }

    /**
     * 校验token
     * @param token
     * @return
     */
    public static Claims checkJWT(String token){

        try {
            final Claims claims = Jwts.parser().setSigningKey(APPSECRET)
                    .parseClaimsJws(token)
                    .getBody();

            return claims;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
