package com.fh.entity.system;

import lombok.Data;

/**
 * 竞猜游戏实体类
 */

public class GuessDetail {
    private Integer guessId;
    private String appUserId;
    private String dollId;
    private String guessType;//最终竞猜结果
    private String guessKey;//1中 0不中
    private Integer guessGold;
    private String createDate;
    private String playbackId;//期号
    private String settlementFlag;//Y 清算 N 未清算
    private String settlementDate;

    public GuessDetail(){

    }

    public GuessDetail(String appUserId, String dollId, String guessKey, Integer guessGold, String playbackId) {
        this.appUserId = appUserId;
        this.dollId = dollId;
        this.guessKey = guessKey;
        this.guessGold = guessGold;
        this.playbackId = playbackId;
    }

    public GuessDetail(String guessKey, String playbackId) {
        this.guessKey = guessKey;
        this.playbackId = playbackId;
    }

    public Integer getGuessId() {
        return guessId;
    }

    public void setGuessId(Integer guessId) {
        this.guessId = guessId;
    }

    public String getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(String appUserId) {
        this.appUserId = appUserId;
    }

    public String getDollId() {
        return dollId;
    }

    public void setDollId(String dollId) {
        this.dollId = dollId;
    }

    public String getGuessType() {
        return guessType;
    }

    public void setGuessType(String guessType) {
        this.guessType = guessType;
    }

    public String getGuessKey() {
        return guessKey;
    }

    public void setGuessKey(String guessKey) {
        this.guessKey = guessKey;
    }

    public Integer getGuessGold() {
        return guessGold;
    }

    public void setGuessGold(Integer guessGold) {
        this.guessGold = guessGold;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getPlaybackId() {
        return playbackId;
    }

    public void setPlaybackId(String playbackId) {
        this.playbackId = playbackId;
    }

    public String getSettlementFlag() {
        return settlementFlag;
    }

    public void setSettlementFlag(String settlementFlag) {
        this.settlementFlag = settlementFlag;
    }

    public String getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(String settlementDate) {
        this.settlementDate = settlementDate;
    }
}
