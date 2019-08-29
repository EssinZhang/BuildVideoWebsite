package net.xdclass.xdvideo.exception;

import net.xdclass.xdvideo.domain.JsonData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常处理控制器
 */
@ControllerAdvice
public class VideoExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonData Handler(Exception e){

        if (e instanceof VideoException){
            VideoException videoException = (VideoException) e;
            return JsonData.buildError(videoException.getMsg(),videoException.getCode());
        }else {
            return JsonData.buildError("全局异常，未知错误");
        }
    }
}
