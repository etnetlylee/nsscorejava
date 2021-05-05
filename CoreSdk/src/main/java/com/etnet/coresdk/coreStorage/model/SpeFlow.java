package com.etnet.coresdk.coreStorage.model;

public class SpeFlow {
    String _code;
    double _flow;
    double _average;
    int _volume;
    double _volumeRate;
    double _turnover;
    double _turnoverRate;

    public String getCode() {
        return this._code;
    }

    public void setCode(String code) {
        this._code = code;
    }

    public double getFlow() {
        return this._flow;
    }

    public void setFlow(double flow) {
        this._flow = flow;
    }

    public double getAverage() {
        return this._average;
    }

    public void setAverage(double average) {
        this._average = average;
    }

    public int getVolume() {
        return this._volume;
    }

    public void setVolume(int volume) {
        this._volume = volume;
    }

    public double getVolumeRate() {
        return this._volumeRate;
    }

    public void setVolumeRate(double volumeRate) {
        this._volumeRate = volumeRate;
    }

    public double getTurnover() {
        return this._turnover;
    }

    public void setTurnover(double turnover) {
        this._turnover = turnover;
    }

    public double getTurnoverRate() {
        return this._turnoverRate;
    }

    public void setTurnoverRate(double turnoverRate) {
        this._turnoverRate = turnoverRate;
    }

}
