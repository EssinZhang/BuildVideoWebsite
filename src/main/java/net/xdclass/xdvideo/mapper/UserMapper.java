package net.xdclass.xdvideo.mapper;

import net.xdclass.xdvideo.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 用户信息数据层
 */
public interface UserMapper {

    /**
     * 根据主键id查找
     * @param id
     * @return
     */
    @Select("select * from user u where u.id = #{id} ")
    User findById (@Param("id") int userId);

    @Select("select * from user u where u.openid = #{openid}")
    User findByOpenId(@Param("openid") String openId);

    /**
     * 保存用户信息
     * @param user
     * @return
     */
    @Insert("INSERT INTO 'user' (`id`, `openid`, `name`, `head_img`, `phone`, `sign`, `sex`, `city`, `create_time`) " +
            "VALUES" +
            "(#{openid},#{name},#{headImg},#{phone},#{sign},#{sex},#{city},#{createTime})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int saveUser(User user);
}
