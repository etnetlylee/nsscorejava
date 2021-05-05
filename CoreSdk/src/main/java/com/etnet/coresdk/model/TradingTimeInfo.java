package com.etnet.coresdk.model;

public class TradingTimeInfo {
    String _amOpen;
    String _amClose;
    String _cutOff;
    String _pmOpen;
    String _pmClose;

    public String getAmOpen() {
        return this._amOpen;
    }

    public void setAmOpen(String value) {
        this._amOpen = value;
    }

    public String getAmClose() {
        return this._amClose;
    }

    public void setAmClose(String value) {
        this._amClose = value;
    }

    public String getCutOff() {
        return this._cutOff;
    }

    public void setCutOff(String value) {
        this._cutOff = value;
    }

    public String getPmOpen() {
        return this._pmOpen;
    }

    public void setPmOpen(String value) {
        this._pmOpen = value;
    }

    public String getPmClose() {
        return this._pmClose;
    }

    public void setPmClose(String value) {
        this._pmClose = value;
    }
}
