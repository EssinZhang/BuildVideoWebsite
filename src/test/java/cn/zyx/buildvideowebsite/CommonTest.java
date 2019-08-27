package cn.zyx.buildvideowebsite;

import io.jsonwebtoken.Claims;
import net.xdclass.xdvideo.domain.User;
import net.xdclass.xdvideo.utils.JwtUtils;
import org.junit.Test;

public class CommonTest {

    @Test
    public void testGeneJwt(){
        User user = new User();

        user.setId(999);
        user.setHeadImg("www.xdclass");
        user.setName("sd");

        String token = JwtUtils.geneJsonWebToken(user);
        System.out.println(token);
    }

    @Test
    public void testCheck(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ4ZGNsYXNzZXMiLCJpZCI6OTk5LCJuYW1lIjoic2QiLCJpbWciOiJ3d3cueGRjbGFzcyIsImlhdCI6MTU2NjM3OTUxOSwiZXhwIjoxNTY2OTg0MzE5fQ.q4zY8YrbJmJflpjuvVVPY2lSL6ny66j3uB9FCoZY2qc";
        Claims claims = JwtUtils.checkJWT(token);
        if (claims != null){
            String name = (String) claims.get("name");
            String img = (String) claims.get("img");
            int id = (Integer) claims.get("id");

            System.out.println(name);
            System.out.println(img);
            System.out.println(id);
        }
    }
}
