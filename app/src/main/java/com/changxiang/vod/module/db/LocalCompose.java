package com.changxiang.vod.module.db;

import java.io.Serializable;

/**
 * Created by 15976 on 2017/1/9.
 */

public class LocalCompose implements Serializable {
    /*0：key   Compose_id
    1：作品名称：Compose_name
    2：作品封面：Compose_image
    3：作品描述：Compose_remark
    4：合成时间（201701040920X）：createDate
    5：合成类型（ compose_type ）：mp3 （0）和MP4（1） //0://录音：1://录像
    6：解码是否完成：Compose_MuxerDecode
    7：合成是否完成：Compose_MuxerTask
    8：是否已经上传（Compose_）：
    9：对应的songid：
    10：开始录制时间：Compose_begin
    11：结束录制时间：Compose_finish
    12：是否删除（预留）Compose_delete
    13：错误编码 Compose_errcode
    14：预留字段：Compose_other
    15: 录制的地址路径 String recordUrl;//
    16: 下载的地址路径 String bzUrl;//
    17:合成之后的地址 Compose_file：
    18：isExport
    */

    String Compose_id;//合成ID
    String Compose_name;//作品名称
    String Compose_image;//作品封面
    String Compose_remark;//作品描述
    String createDate;//合成时间
    String compose_type; //0://录音：1://录像
    String Compose_MuxerDecode;//解码是否完成 空：为开始解码 0：在解码中；1解码成功；2：解码失败
    String Compose_MuxerTask;//是否合成成功：0：初始状态（刚刚入库）；1：已经合成完成；2：等待合成状态；:3：合成进行中；4：合成失败（文件不存在）
    String isUpload;//是否上传：0：初始状态（刚刚入库）；1：已经上传完成；2：等待上传状态；:3：上传进行中；4：上传失败（文件不存在）
    String songId;//音乐源ID
    String activityId;//活动
    String Compose_begin;
    String Compose_finish;
    String allDuration;//音乐源总时长：
    String Compose_other;//暂时保存KRC
    String recordUrl;//录制的地址路径
    String bzUrl;//下载的地址路径
    String isExport;//是否导出  1 : 代表没有选中     !1 ： 代表选中
    String Compose_delete;
    String Compose_file;//合成之后的地址
    String decodeUrl;//伴奏解码文件地址：


    String offsetNum = "0";//人声偏移，毫秒（不入库）
    String VoiceWeight = "50";//人声音量权重（不入库）
    String BackgroundWeight = "50";//伴奏音量权重（不入库）


    public LocalCompose() {
    }

    public LocalCompose(String compose_id, String compose_name, String compose_image, String compose_remark, String createDate, String compose_type, String compose_MuxerDecode, String compose_MuxerTask, String isUpload, String songId, String activityId, String compose_begin, String compose_finish, String allDuration, String compose_other, String recordUrl, String bzUrl, String isExport, String compose_delete, String compose_file, String decodeUrl, String offsetNum, String voiceWeight, String backgroundWeight) {
        Compose_id = compose_id;
        Compose_name = compose_name;
        Compose_image = compose_image;
        Compose_remark = compose_remark;
        this.createDate = createDate;
        this.compose_type = compose_type;
        Compose_MuxerDecode = compose_MuxerDecode;
        Compose_MuxerTask = compose_MuxerTask;
        this.isUpload = isUpload;
        this.songId = songId;
        this.activityId = activityId;
        Compose_begin = compose_begin;
        Compose_finish = compose_finish;
        this.allDuration = allDuration;
        Compose_other = compose_other;
        this.recordUrl = recordUrl;
        this.bzUrl = bzUrl;
        this.isExport = isExport;
        Compose_delete = compose_delete;
        Compose_file = compose_file;
        this.decodeUrl = decodeUrl;
        this.offsetNum = offsetNum;
        VoiceWeight = voiceWeight;
        BackgroundWeight = backgroundWeight;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getVoiceWeight() {
        return VoiceWeight;
    }

    public void setVoiceWeight(String voiceWeight) {
        VoiceWeight = voiceWeight;
    }

    public String getBackgroundWeight() {
        return BackgroundWeight;
    }

    public void setBackgroundWeight(String backgroundWeight) {
        BackgroundWeight = backgroundWeight;
    }

    public String getOffsetNum() {
        return offsetNum;
    }

    public void setOffsetNum(String offsetNum) {
        this.offsetNum = offsetNum;
    }

    public String getDecodeUrl() {
        return decodeUrl;
    }

    public void setDecodeUrl(String decodeUrl) {
        this.decodeUrl = decodeUrl;
    }

    public String getAllDuration() {
        return allDuration;
    }

    public void setAllDuration(String allDuration) {
        this.allDuration = allDuration;
    }

    public String getBzUrl() {
        return bzUrl;
    }

    public void setBzUrl(String bzUrl) {
        this.bzUrl = bzUrl;
    }

    public String getCompose_begin() {
        return Compose_begin;
    }

    public void setCompose_begin(String compose_begin) {
        Compose_begin = compose_begin;
    }

    public String getCompose_delete() {
        return Compose_delete;
    }

    public void setCompose_delete(String compose_delete) {
        Compose_delete = compose_delete;
    }

    public String getCompose_file() {
        return Compose_file;
    }

    public void setCompose_file(String compose_file) {
        Compose_file = compose_file;
    }

    public String getCompose_finish() {
        return Compose_finish;
    }

    public void setCompose_finish(String compose_finish) {
        Compose_finish = compose_finish;
    }

    public String getCompose_id() {
        return Compose_id;
    }

    public void setCompose_id(String compose_id) {
        Compose_id = compose_id;
    }

    public String getCompose_image() {
        return Compose_image;
    }

    public void setCompose_image(String compose_image) {
        Compose_image = compose_image;
    }

    public String getCompose_MuxerDecode() {
        return Compose_MuxerDecode;
    }

    public void setCompose_MuxerDecode(String compose_MuxerDecode) {
        Compose_MuxerDecode = compose_MuxerDecode;
    }

    public String getCompose_MuxerTask() {
        return Compose_MuxerTask;
    }

    public void setCompose_MuxerTask(String compose_MuxerTask) {
        Compose_MuxerTask = compose_MuxerTask;
    }

    public String getCompose_name() {
        return Compose_name;
    }

    public void setCompose_name(String compose_name) {
        Compose_name = compose_name;
    }

    public String getCompose_other() {
        return Compose_other;
    }

    public void setCompose_other(String compose_other) {
        Compose_other = compose_other;
    }

    public String getCompose_remark() {
        return Compose_remark;
    }

    public void setCompose_remark(String compose_remark) {
        Compose_remark = compose_remark;
    }

    public String getCompose_type() {
        return compose_type;
    }

    public void setCompose_type(String compose_type) {
        this.compose_type = compose_type;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getIsExport() {
        return isExport;
    }

    public void setIsExport(String isExport) {
        this.isExport = isExport;
    }

    public String getIsUpload() {
        return isUpload;
    }

    public void setIsUpload(String isUpload) {
        this.isUpload = isUpload;
    }

    public String getRecordUrl() {
        return recordUrl;
    }

    public void setRecordUrl(String recordUrl) {
        this.recordUrl = recordUrl;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }
}
