package coreStorage.model;

public class SpeStock {
    String _brokerCode;
    int _volume;
    double _turnover;
    double _average;
    int _buyVolume;
    int _sellVolume;
    int _differVolume;
    double _buyTurnover;
    double _sellTurnover;
    double _differTurnover;

    public String getBrokerCode() {
        return this._brokerCode;
    }

    public void setBrokerCode(String brokerCode) {
        this._brokerCode = brokerCode;
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
