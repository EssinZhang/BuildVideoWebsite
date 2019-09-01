package net.xdclass.xdvideo.mapper;

import net.xdclass.xdvideo.domain.VideoOrder;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 视频订单数据层
 */
public interface VideoOrderMapper {

    @Insert("insert into `video_order` (`openid`, `out_trade_no`, `state`, `create_time`, `notify_time`, `total_fee`, `nickname`, `head_img`, `video_id`, `video_title`, `video_img`, `user_id`, `ip`, `del`) " +
            "values" +
            "(#{openid},#{outTradeNo},#{state},#{createTime},#{notifyTime},#{totalFee},#{nickname},#{headImg},#{videoId},#{videoTitle},#{videoImg},#{userId},#{ip},#{del})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    int insertVideoOrder(VideoOrder videoOrder);

    /**
     * 根据主键id查找订单
     * @param id
     * @return
     */
    @Select("select * from video_order where id=#{order_id} and del=0")
    VideoOrder findOrderById(@Param("order_id") int id);

    /**
     * 根据交易订单号获取订单对象
     * @param id
     * @return
     */
    @Select("select * from video_order where out_trade_no=#{out_trade_no} and del=0")
    VideoOrder findOrderByOutTradeNo(@Param("out_trade_no") String id);

    /**
     * 根据主键id和用户id 进行逻辑删除订单   （将del字段设置为1）
     * @param id
     * @param userId
     * @return
     */
    @Update("update video_order set del=1 where id=#{id} and user_id=#{user_id}")
    int delOrder(@Param("id") int id,@Param("user_id") int userId);

    /**
     * 根据用户id查询该用户下所有订单
     * @param userId
     * @return
     */
    @Select("select out_trade_no,state,create_time,notify_time,total_fee,nickname,video_id,video_title,del from video_order where user_id=#{user_id} order by create_time desc")
    List<VideoOrder> findMyOrderList(@Param("user_id") int userId);

    /**
     * 根据订单流水号 更新 订单状态 回调时间 openid
     * @param videoOrder
     * @return
     */
    @Update("update video_order set state=#{state}, notify_time=#{notifyTime}, openid=#{openid}" +
            "where out_trade_no=#{outTradeNo} and state=0 and del=0")
    int updateVideoOrderByOutTradeNo(VideoOrder videoOrder);


}
