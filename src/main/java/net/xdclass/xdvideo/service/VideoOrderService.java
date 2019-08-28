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

    VideoOrder findOrderByOutTradeNo(int id);

    int delOrder(int id,int userId);

    List<VideoOrder> findMyOrderList(int userId);

    int updateVideoOrderByOutTradeNo(VideoOrder videoOrder);
}
