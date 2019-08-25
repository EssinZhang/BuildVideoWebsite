package net.xdclass.xdvideo.domain;


import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体类
 */
public class User implements Serializable {

  private Integer id;
  private String openid;
  private String name;
  private String headImg;
  private String phone;
  private String sign;
  private Integer sex;
  private String city;
  private java.util.Date createTime;

  public User() {
  }

  public User(Integer id, String openid, String name, String headImg, String phone, String sign, Integer sex, String city, Date createTime) {
    this.id = id;
    this.openid = openid;
    this.name = name;
    this.headImg = headImg;
    this.phone = phone;
    this.sign = sign;
    this.sex = sex;
    this.city = city;
    this.createTime = createTime;
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


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getHeadImg() {
    return headImg;
  }

  public void setHeadImg(String headImg) {
    this.headImg = headImg;
  }


  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }


  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }


  public Integer getSex() {
    return sex;
  }

  public void setSex(Integer sex) {
    this.sex = sex;
  }


  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }


  public java.util.Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.util.Date createTime) {
    this.createTime = createTime;
  }

}
