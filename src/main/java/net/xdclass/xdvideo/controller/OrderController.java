package net.xdclass.xdvideo.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import net.xdclass.xdvideo.domain.JsonData;
import net.xdclass.xdvideo.dto.VideoOrderDto;
import net.xdclass.xdvideo.service.VideoOrderService;
import net.xdclass.xdvideo.utils.IpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 订单接口
 */
@RestController
//@RequestMapping("/user/api/v1/order")
@RequestMapping("/api/v1/order")
public class OrderController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Logger dataLogger = LoggerFactory.getLogger("dataLogger");

    @Autowired
    private VideoOrderService videoOrderService;

    @GetMapping("add")
    public JsonData saveOrder(@RequestParam(value = "video_id",required = true) int video_id,
                              @RequestParam(value = "user_id",required = true) int user_id,
                              HttpServletRequest request,
                              HttpServletResponse response) throws Exception {

        String ip = IpUtils.getIpAddr(request);
        //int userId = (Integer) request.getAttribute("user_id");
        //int userId = 3;
        VideoOrderDto videoOrderDto = new VideoOrderDto();
        videoOrderDto.setUserId(user_id);
        videoOrderDto.setVideoId(video_id);
        videoOrderDto.setIp(ip);

        String codeUrl = videoOrderService.save(videoOrderDto);

        if (codeUrl == null){
            System.out.println("codeUrl == null");
            throw new NullPointerException();
        }

        try {
            //生成二维码
            Map<EncodeHintType,Object> hints = new HashMap<>();
            //设置纠错等级
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            //编码类型
            hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");

            BitMatrix bitMatrix = new MultiFormatWriter().encode(codeUrl, BarcodeFormat.QR_CODE,400,400);
            OutputStream out = response.getOutputStream();

            MatrixToImageWriter.writeToStream(bitMatrix,"png",out);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return JsonData.buildSuccess("下单成功");
    }
}
