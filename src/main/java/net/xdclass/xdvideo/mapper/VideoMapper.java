package net.xdclass.xdvideo.mapper;

import net.xdclass.xdvideo.domain.Video;
import net.xdclass.xdvideo.provider.VideoProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * video数据访问层
 */
//@Mapper
public interface VideoMapper {

    /**
     * 通过查找video表中所有数据测试数据库连接
     * @return
     */
    @Select("select * from video")
    List<Video> findAll();

    /*根据id查找视频信息*/
    @Select("SELECT * FROM video WHERE id = #{id}")
    Video findById(int id);

    /*更新视频信息*/
    //@Update("UPDATE video SET title=#{title} WHERE id =#{id}")
    @UpdateProvider(type = VideoProvider.class,method = "updateVideo")
    int update(Video Video);

    /*根据id删除视频信息*/
    @Delete("DELETE FROM video WHERE id =#{id}")
    int delete(int id);

    /*插入视频信息*/
    @Insert("INSERT INTO `video` ( `title`, `summary`, " +
            "`cover_img`, `view_num`, `price`, `create_time`," +
            " `online`, `point`)" +
            "VALUES" +
            "(#{title}, #{summary}, #{coverImg}, #{viewNum}, #{price},#{createTime}" +
            ",#{online},#{point});")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int save(Video video);
}
