package net.xdclass.xdvideo.domain;


import java.io.Serializable;
import java.util.Date;

/**
 * 视频章节实体类
 */
public class Chapter implements Serializable {

  private Integer id;
  private Integer videoId;
  private String title;
  private Integer ordered;
  private java.util.Date createTime;

  public Chapter() {
  }

  public Chapter(Integer id, Integer videoId, String title, Integer ordered, Date createTime) {
    this.id = id;
    this.videoId = videoId;
    this.title = title;
    this.ordered = ordered;
    this.createTime = createTime;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public Integer getVideoId() {
    return videoId;
  }

  public void setVideoId(Integer videoId) {
    this.videoId = videoId;
  }


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  public Integer getOrdered() {
    return ordered;
  }

  public void setOrdered(Integer ordered) {
    this.ordered = ordered;
  }


  public java.util.Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.util.Date createTime) {
    this.createTime = createTime;
  }

}
