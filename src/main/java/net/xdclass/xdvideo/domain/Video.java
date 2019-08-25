package net.xdclass.xdvideo.domain;


import java.io.Serializable;
import java.util.Date;

/**
 * 视频实体类
 */
public class Video implements Serializable {

  private Integer id;
  private String title;
  private String summary;
  private String coverImg;
  private Integer viewNum;
  private Integer price;
  private java.util.Date createTime;
  private Integer online;
  private Double point;

  public Video() {
  }

  public Video(Integer id, String title, String summary, String coverImg, Integer viewNum, Integer price, Date createTime, Integer online, double point) {
    this.id = id;
    this.title = title;
    this.summary = summary;
    this.coverImg = coverImg;
    this.viewNum = viewNum;
    this.price = price;
    this.createTime = createTime;
    this.online = online;
    this.point = point;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }


  public String getCoverImg() {
    return coverImg;
  }

  public void setCoverImg(String coverImg) {
    this.coverImg = coverImg;
  }


  public Integer getViewNum() {
    return viewNum;
  }

  public void setViewNum(Integer viewNum) {
    this.viewNum = viewNum;
  }


  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }


  public java.util.Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.util.Date createTime) {
    this.createTime = createTime;
  }


  public Integer getOnline() {
    return online;
  }

  public void setOnline(Integer online) {
    this.online = online;
  }


  public Double getPoint() {
    return point;
  }

  public void setPoint(Double point) {
    this.point = point;
  }

}
