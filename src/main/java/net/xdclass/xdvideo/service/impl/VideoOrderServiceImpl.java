package net.xdclass.xdvideo.service.impl;

import net.xdclass.xdvideo.config.WeChatConfig;
import net.xdclass.xdvideo.domain.User;
import net.xdclass.xdvideo.domain.Video;
import net.xdclass.xdvideo.domain.VideoOrder;
import net.xdclass.xdvideo.dto.VideoOrderDto;
import net.xdclass.xdvideo.mapper.UserMapper;
import net.xdclass.xdvideo.mapper.VideoMapper;
import net.xdclass.xdvideo.mapper.VideoOrderMapper;
import net.xdclass.xdvideo.service.VideoOrderService;
import net.xdclass.xdvideo.utils.CommonUtils;
import net.xdclass.xdvideo.utils.HttpUtils;
import net.xdclass.xdvideo.utils.WeChatPayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class VideoOrderServiceImpl implements VideoOrderService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Logger dataLogger = LoggerFactory.getLogger("dataLogger");


    @Autowired
    private WeChatConfig weChatConfig;

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private VideoOrderMapper videoOrderMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)//开启事务
    public String save(VideoOrderDto videoOrderDto) throws Exception {

        dataLogger.info("module=video_order|user_id={}|video_id={}",videoOrderDto.getUserId(),videoOrderDto.getVideoId());

        //查找视频信息
        Video video = videoMapper.findById(videoOrderDto.getVideoId());

        //查找用户信息
        User user = userMapper.findById(videoOrderDto.getUserId());

        //生成订单
        VideoOrder videoOrder = new VideoOrder();
        videoOrder.setVideoId(video.getId());
        videoOrder.setTotalFee(video.getPrice());
        videoOrder.setVideoImg(video.getCoverImg());
        videoOrder.setVideoTitle(video.getTitle());
        videoOrder.setCreateTime(new Date());

        videoOrder.setState(0);
        videoOrder.setUserId(user.getId());
        videoOrder.setHeadImg(user.getHeadImg());
        videoOrder.setNickname(user.getName());

        videoOrder.setDel(0);
        videoOrder.setIp(videoOrderDto.getIp());
        videoOrder.setOutTradeNo(CommonUtils.generateUUID());
        videoOrderMapper.insertVideoOrder(videoOrder);

        //生成签名并获取code_url
        String unifiedOrder = unifiedOrder(videoOrder);

        return unifiedOrder;
    }

    /**
     * 统一下单方法
     *
     * 生成签名并获取code_url
     * @return
     */
    private String unifiedOrder(VideoOrder videoOrder) throws Exception {
        //生成签名
        SortedMap<String,String> params = new TreeMap<>();
        params.put("appid",weChatConfig.getAppId());
        params.put("mch_id",weChatConfig.getMchId());
        params.put("nonce_str",CommonUtils.generateUUID());
        params.put("body",videoOrder.getVideoTitle());
        params.put("out_trade_no",videoOrder.getOutTradeNo());
        params.put("total_fee",videoOrder.getTotalFee().toString());
        params.put("spbill_create_ip",videoOrder.getIp());
        params.put("notify_url",weChatConfig.getPayCallbackUrl());
        params.put("trade_type","NATIVE");

        //sign签名
        String sign = WeChatPayUtils.createSign(params,weChatConfig.getKey());
        params.put("sign",sign);

        //map转xml
        String payXml = null;
        payXml = WeChatPayUtils.mapToXml(params);
        System.out.println(payXml);

        //统一下单
        String orderStr = HttpUtils.doPost(WeChatConfig.getUnifiedOrderUrl(), payXml, 4000);
        if (orderStr == null){
            return null;
        }
        Map<String,String> unifiedOrderMap = WeChatPayUtils.xmlToMap(orderStr);
        System.out.println(unifiedOrderMap.toString());
        if (unifiedOrderMap != null){
            return unifiedOrderMap.get("code_url");
        }


        return "";
    }

    @Override
    public int insertVideoOrder(VideoOrder videoOrder) {
        return 0;
    }

    @Override
    public VideoOrder findOrderById(int id) {
        return null;
    }

    /**
     * 根据流水号查找订单
     * @param id
     * @return
     */
    @Override
    public VideoOrder findOrderByOutTradeNo(String id) {
        return videoOrderMapper.findOrderByOutTradeNo(id);
    }

    @Override
    public int delOrder(int id, int userId) {
        return 0;
    }

    /**
     * 根据用户id查该用户所有订单
     * @param userId
     * @return
     */
    @Override
    public List<VideoOrder> findMyOrderList(int userId) {
        return videoOrderMapper.findMyOrderList(userId);
    }

    /**
     * 根据流水号更新订单状态
     * @param videoOrder
     * @return
     */
    @Override
    public int updateVideoOrderByOutTradeNo(VideoOrder videoOrder) {

        return videoOrderMapper.updateVideoOrderByOutTradeNo(videoOrder);
    }
}
