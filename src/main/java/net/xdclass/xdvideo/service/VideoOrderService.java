package net.xdclass.xdvideo.service;

import net.xdclass.xdvideo.domain.Video;
import net.xdclass.xdvideo.domain.VideoOrder;
import net.xdclass.xdvideo.dto.VideoOrderDto;

import java.util.List;

/**
 * 订单service接口
 */
public interface VideoOrderService {

    /**
     * 下单接口
     * @param videoOrderDto
     * @return
     */
    String save(VideoOrderDto videoOrderDto) throws Exception;

    int insertVideoOrder(VideoOrder videoOrder);

    VideoOrder findOrderById(int id);

    /**
     * 根据流水号查找订单
     * @param id
     * @return
     */
    VideoOrder findOrderByOutTradeNo(String id);

    int delOrder(int id,int userId);

    List<VideoOrder> findMyOrderList(int userId);

    /**
     * 根据流水号更新订单状态
     * @param videoOrder
     * @return
     */
    int updateVideoOrderByOutTradeNo(VideoOrder videoOrder);
}
