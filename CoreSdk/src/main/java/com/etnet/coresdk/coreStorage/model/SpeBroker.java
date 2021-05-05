package com.etnet.coresdk.coreStorage.model;

public class SpeBroker {
    String _stockCode;
    int _volume;
    double _turnover;
    double _average;
    int _buyVolume;
    int _sellVolume;
    int _differVolume;
    double _buyTurnover;
    double _sellTurnover;
    double _differTurnover;
    String _type; // type=STK, WAR, TFT, CBBC

    public String getType() {
        return this._type;
    }

    public void setType(String type) {
        this._type = type;
    }

    public String getStockCode() {
        return this._stockCode;
    }

    public void setStockCode(String stockCode) {
        this._stockCode = stockCode;
    }

    public int getVolume() {
        return this._volume;
    }

    public void setVolume(int volume) {
        this._volume = volume;
    }

    public double getTurnover() {
        return this._turnover;
    }

    public void setTurnover(double turnover) {
        this._turnover = turnover;
    }

    public double getAverage() {
        return this._average;
    }

    public void setAverage(double average) {
        this._average = average;
    }

    public int getBuyVolume() {
        return this._buyVolume;
    }

    public void setBuyVolume(int buyVolume) {
        this._buyVolume = buyVolume;
    }

    public int getSellVolume() {
        return this._sellVolume;
    }

    public void setSellVolume(int sellVolume) {
        this._sellVolume = sellVolume;
    }

    public int getDifferVolume() {
        return this._differVolume;
    }

    public void setDifferVolume(int differVolume) {
        this._differVolume = differVolume;
    }

    public double getBuyTurnover() {
        return this._buyTurnover;
    }

    public void setBuyTurnover(double buyTurnover) {
        this._buyTurnover = buyTurnover;
    }

    public double getSellTurnover() {
        return this._sellTurnover;
    }

    public void setSellTurnover(double sellTurnover) {
        this._sellTurnover = sellTurnover;
    }

    public double getDifferTurnover() {
        return this._differTurnover;
    }

    public void setDifferTurnover(double differTurnover) {
        this._differTurnover = differTurnover;
    }

}
