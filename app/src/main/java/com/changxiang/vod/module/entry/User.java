package com.changxiang.vod.module.entry;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * 用户实体类
 *
 * @author admin
 *         方式一：Code-->Generate
 *         <p>
 *         方式二：通过快捷键Alt+Insert
 */
public class User implements Serializable {


    /**
     * id : 10
     * userId : 1959141017
     * userName : 有意义
     * sex : 1
     * birthDay : 1990-01-01
     * phone : 18899776590
     * password : F928663420E340D420EC58F757AD6D72
     * imgHead : http://test.app.srv.quchangkeji.com:8888/group1/M00/00/37/wKgDQlow3cKALVHEAADTEJaN0sc222.png
     * imgHome : null
     * idCard :
     * realName : null
     * isReal : null
     * authMethod : null
     * img1 : null
     * img2 : null
     * qq : null
     * weibo : null
     * wechat : null
     * deviceSn : 7a6d39b107041159
     * bankNo : null
     * userLevel : 1
     * lng : null
     * lat : null
     * score : 0
     * flowerQuantity : 25
     * attentionQuantity : -6
     * fanQuantity : 0
     * accessQuantity : 0
     * clientType : null
     * userType : null
     * state : 0
     * remark : 888888888
     * isUpload : 1
     * createDate : 2017-12-11 12:39:56
     * modifyDate : 2017-12-11 12:39:56
     * levelName : 草根歌手
     * imgLevel : http://192.168.3.137:8080/kingpk-service/files/dj/home/caogen.png
     * isSign : 1
     * isNewUser : 0
     * openId : a5c2b775256247ca8b25f4d41244c9a4
     */

    private String id;
    private String userId;
    private String userName;
    private String sex="1";
    private String birthDay;
    private String phone;
    private String password;
    private String imgHead;
    private String imgHome;
    private String idCard;
    private String realName;
    private String isReal;
    private String authMethod;
    private String img1;
    private String img2;
    private String qq;
    private String weibo;
    private String wechat;
    private String deviceSn;
    private String bankNo;
    private String userLevel;
    private String lng;
    private String lat;
    private int score;
    private int flowerQuantity;
    private int attentionQuantity;
    private int fanQuantity;
    private int accessQuantity;
    private String clientType;
    private String userType;
    private String state;
    private String remark;
    private String isUpload;
    private String createDate;
    private String modifyDate;
    private String levelName;
    private String imgLevel;
    private String isSign;
    private String isNewUser;
    private String openId;

    private String activityPhone;

    private String addressId;
    private String receiveUserName;//receiveUserName
    private String receivePhone;
    private String receiveProvince;
    private String receiveCity;
    private String receiveDistrict;
    private String receiveAddress;
    private double isDeposit;

    public String getActivityPhone() {
        return activityPhone;
    }

    public void setActivityPhone(String activityPhone) {
        this.activityPhone = activityPhone;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getReceiveUserName() {
        return receiveUserName;
    }

    public void setReceiveUserName(String receiveUserName) {
        this.receiveUserName = receiveUserName;
    }

    public String getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
    }

    public String getReceiveProvince() {
        return receiveProvince;
    }

    public void setReceiveProvince(String receiveProvince) {
        this.receiveProvince = receiveProvince;
    }

    public String getReceiveCity() {
        return receiveCity;
    }

    public void setReceiveCity(String receiveCity) {
        this.receiveCity = receiveCity;
    }

    public String getReceiveDistrict() {
        return receiveDistrict;
    }

    public void setReceiveDistrict(String receiveDistrict) {
        this.receiveDistrict = receiveDistrict;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public double getIsDeposit() {
        return isDeposit;
    }

    public void setIsDeposit(double isDeposit) {
        this.isDeposit = isDeposit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImgHead() {
        return imgHead;
    }

    public void setImgHead(String imgHead) {
        this.imgHead = imgHead;
    }

    public String getImgHome() {
        return imgHome;
    }

    public void setImgHome(String imgHome) {
        this.imgHome = imgHome;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIsReal() {
        return isReal;
    }

    public void setIsReal(String isReal) {
        this.isReal = isReal;
    }

    public String getAuthMethod() {
        return authMethod;
    }

    public void setAuthMethod(String authMethod) {
        this.authMethod = authMethod;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getFlowerQuantity() {
        return flowerQuantity;
    }

    public void setFlowerQuantity(int flowerQuantity) {
        this.flowerQuantity = flowerQuantity;
    }

    public int getAttentionQuantity() {
        return attentionQuantity;
    }

    public void setAttentionQuantity(int attentionQuantity) {
        this.attentionQuantity = attentionQuantity;
    }

    public int getFanQuantity() {
        return fanQuantity;
    }

    public void setFanQuantity(int fanQuantity) {
        this.fanQuantity = fanQuantity;
    }

    public int getAccessQuantity() {
        return accessQuantity;
    }

    public void setAccessQuantity(int accessQuantity) {
        this.accessQuantity = accessQuantity;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIsUpload() {
        return isUpload;
    }

    public void setIsUpload(String isUpload) {
        this.isUpload = isUpload;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getImgLevel() {
        return imgLevel;
    }

    public void setImgLevel(String imgLevel) {
        this.imgLevel = imgLevel;
    }

    public String getIsSign() {
        return isSign;
    }

    public void setIsSign(String isSign) {
        this.isSign = isSign;
    }

    public String getIsNewUser() {
        return isNewUser;
    }

    public void setIsNewUser(String isNewUser) {
        this.isNewUser = isNewUser;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
