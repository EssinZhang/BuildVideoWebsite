package net.xdclass.xdvideo.domain;


import java.io.Serializable;
import java.util.Date;

/**
 * 视频订单实体类
 */
public class VideoOrder implements Serializable {

  private Integer id;
  private String openid;
  private String outTradeNo;
  /**
   * 0表示未支付，1表示已经支付
   */
  private Integer state;
  private java.util.Date createTime;
  private java.util.Date notifyTime;
  /**
   *分为单位
   */
  private Integer totalFee;
  private String nickname;
  private String headImg;
  private Integer videoId;
  private String videoTitle;
  private String videoImg;
  private Integer userId;
  private String ip;
  private Integer del;

  public VideoOrder() {
  }

  public VideoOrder(Integer id, String openid, String outTradeNo, Integer state, Date createTime, Date notifyTime, Integer totalFee, String nickname, String headImg, Integer videoId, String videoTitle, String videoImg, Integer userId, String ip, Integer del) {
    this.id = id;
    this.openid = openid;
    this.outTradeNo = outTradeNo;
    this.state = state;
    this.createTime = createTime;
    this.notifyTime = notifyTime;
    this.totalFee = totalFee;
    this.nickname = nickname;
    this.headImg = headImg;
    this.videoId = videoId;
    this.videoTitle = videoTitle;
    this.videoImg = videoImg;
    this.userId = userId;
    this.ip = ip;
    this.del = del;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public String getOpenid() {
    return openid;
  }

  public void setOpenid(String openid) {
    this.openid = openid;
  }


  public String getOutTradeNo() {
    return outTradeNo;
  }

  public void setOutTradeNo(String outTradeNo) {
    this.outTradeNo = outTradeNo;
  }


  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
  }


  public java.util.Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.util.Date createTime) {
    this.createTime = createTime;
  }


  public java.util.Date getNotifyTime() {
    return notifyTime;
  }

  public void setNotifyTime(java.util.Date notifyTime) {
    this.notifyTime = notifyTime;
  }


  public Integer getTotalFee() {
    return totalFee;
  }

  public void setTotalFee(Integer totalFee) {
    this.totalFee = totalFee;
  }


  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }


  public String getHeadImg() {
    return headImg;
  }

  public void setHeadImg(String headImg) {
    this.headImg = headImg;
  }


  public Integer getVideoId() {
    return videoId;
  }

  public void setVideoId(Integer videoId) {
    this.videoId = videoId;
  }


  public String getVideoTitle() {
    return videoTitle;
  }

  public void setVideoTitle(String videoTitle) {
    this.videoTitle = videoTitle;
  }


  public String getVideoImg() {
    return videoImg;
  }

  public void setVideoImg(String videoImg) {
    this.videoImg = videoImg;
  }


  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }


  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }


  public Integer getDel() {
    return del;
  }

  public void setDel(Integer del) {
    this.del = del;
  }

}
