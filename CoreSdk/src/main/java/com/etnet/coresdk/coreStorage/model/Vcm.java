package com.etnet.coresdk.coreStorage.model;

public class Vcm {
    int _seqNo;
    String _startTime;
    String _endTime;
    double _refPrice;
    double _upLimit;
    double _lowLimit;
    boolean _onOff;

    public int getSeqNo() {
        return this._seqNo;
    }

    public void setSeqNo(int seqNo) {
        this._seqNo = seqNo;
    }

    public String getStartTime() {
        return this._startTime;
    }

    public void setStartTime(String startTime) {
        this._startTime = startTime;
    }

    public String getEndTime() {
        return this._endTime;
    }

    public void setEndTime(String endTime) {
        this._endTime = endTime;
    }

    public double getRefPrice() {
        return this._refPrice;
    }

    public void setRefPrice(double refPrice) {
        this._refPrice = refPrice;
    }

    public double getUpLimit() {
        return this._upLimit;
    }

    public void setUpLimit(double upLimit) {
        this._upLimit = upLimit;
    }

    public double getLowLimit() {
        return this._lowLimit;
    }

    public void setLowLimit(double lowLimit) {
        this._lowLimit = lowLimit;
    }

    public boolean isOnOff() {
        return this._onOff;
    }

    public void setOnOff(boolean onOff) {
        this._onOff = onOff;
    }
}
