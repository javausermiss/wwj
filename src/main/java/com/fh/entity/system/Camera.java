package com.fh.entity.system;

import java.sql.Timestamp;

/**
 * 摄像头实体类
 */
public class Camera {
    private String C_ID;
    private String DOLL_ID;
    private String CAMERA_NAME;
    private String CAMERA_NUM;
    private String RTMP_URL;
    private String SERVER_NAME;
    private String LIVESTREAM;
    private String SERVER_ID;
    private String DEVICE_STATE;
    private Timestamp CREATE_DATE;
    private Timestamp UPDATE_DATE;
    private Integer LOCK_VERSION;

    public Camera() {

    }

    public String getC_ID() {
        return C_ID;
    }

    public void setC_ID(String c_ID) {
        C_ID = c_ID;
    }

    public String getDOLL_ID() {
        return DOLL_ID;
    }

    public void setDOLL_ID(String DOLL_ID) {
        this.DOLL_ID = DOLL_ID;
    }

    public String getCAMERA_NAME() {
        return CAMERA_NAME;
    }

    public void setCAMERA_NAME(String CAMERA_NAME) {
        this.CAMERA_NAME = CAMERA_NAME;
    }

    public String getCAMERA_NUM() {
        return CAMERA_NUM;
    }

    public void setCAMERA_NUM(String CAMERA_NUM) {
        this.CAMERA_NUM = CAMERA_NUM;
    }

    public String getRTMP_URL() {
        return RTMP_URL;
    }

    public void setRTMP_URL(String RTMP_URL) {
        this.RTMP_URL = RTMP_URL;
    }

    public String getSERVER_NAME() {
        return SERVER_NAME;
    }

    public void setSERVER_NAME(String SERVER_NAME) {
        this.SERVER_NAME = SERVER_NAME;
    }

    public String getLIVESTREAM() {
        return LIVESTREAM;
    }

    public void setLIVESTREAM(String LIVESTREAM) {
        this.LIVESTREAM = LIVESTREAM;
    }

    public String getSERVER_ID() {
        return SERVER_ID;
    }

    public void setSERVER_ID(String SERVER_ID) {
        this.SERVER_ID = SERVER_ID;
    }

    public String getDEVICE_STATE() {
        return DEVICE_STATE;
    }

    public void setDEVICE_STATE(String DEVICE_STATE) {
        this.DEVICE_STATE = DEVICE_STATE;
    }

    public Timestamp getCREATE_DATE() {
        return CREATE_DATE;
    }

    public void setCREATE_DATE(Timestamp CREATE_DATE) {
        this.CREATE_DATE = CREATE_DATE;
    }

    public Timestamp getUPDATE_DATE() {
        return UPDATE_DATE;
    }

    public void setUPDATE_DATE(Timestamp UPDATE_DATE) {
        this.UPDATE_DATE = UPDATE_DATE;
    }

    public Integer getLOCK_VERSION() {
        return LOCK_VERSION;
    }

    public void setLOCK_VERSION(Integer LOCK_VERSION) {
        this.LOCK_VERSION = LOCK_VERSION;
    }
}
