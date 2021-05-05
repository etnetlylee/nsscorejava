package com.etnet.coresdk.coreStorage.model;

public class WarrantChartRecord {
    int _timestamp;
    double _avgpricebuy;
    double _lpbuy;
    double _avgpricesell;
    double _lpsell;
    double _outstanding;
    double _outstandingpercent;
    double _sharestraded;
    double _numofwar;
    double _iv;
    double _hv20;
    double _rsi9;
    double _rsi14;
    double _underlyhv;
    double _close;

    public WarrantChartRecord(
            int timestamp,
            double avgpricebuy,
            double lpbuy,
            double avgpricesell,
            double lpsell,
            double outstanding,
            double outstandingpercent,
            double sharestraded,
            double numofwar,
            double iv,
            double hv20,
            double rsi9,
            double rsi14,
            double underlyhv,
            double close) {
        this._timestamp = timestamp;
        this._avgpricebuy = avgpricebuy;
        this._lpbuy = lpbuy;
        this._avgpricesell = avgpricesell;
        this._lpsell = lpsell;
        this._outstanding = outstanding;
        this._outstandingpercent = outstandingpercent;
        this._sharestraded = sharestraded;
        this._numofwar = numofwar;
        this._iv = iv;
        this._hv20 = hv20;
        this._rsi9 = rsi9;
        this._rsi14 = rsi14;
        this._underlyhv = underlyhv;
        this._close = close;
    }

    public int getTimeStamp(){
        return this._timestamp;
    }

    public void setTimeStamp(int timestamp){
        this._timestamp = timestamp;
    }

    public double getAvgPriceBuy(){
        return this._avgpricebuy;
    }

    public void setAvgPriceBuy(double avgpricebuy){
        this._avgpricebuy = avgpricebuy;
    }

    public double getLpBuy(){
        return this._lpbuy;
    }

    public void setLpBuy(double lpbuy){
        this._lpbuy = lpbuy;
    }

    public double getAvgPriceSell(){
        return this._avgpricesell;
    }

    public void setAvgPriceSell(double avgpricesell){
        this._avgpricesell = avgpricesell;
    }

    public double getLpSell(){
        return this._lpsell;
    }

    public void setLpSell(double lpsell){
        this._lpsell = lpsell;
    }

    public double getOutstanding(){
        return this._outstanding;
    }

    public void setOutstanding(double outstanding){
        this._outstanding = outstanding;
    }

    public double getOutstandingPercent(){
        return this._outstandingpercent;
    }

    public void setOutstandingPercent(double outstandingpercent){
        this._outstandingpercent = outstandingpercent;
    }

    public double getSharesTraded(){
        return this._sharestraded;
    }

    public void setSharesTraded(double sharestraded){
        this._sharestraded = sharestraded;
    }

    public double getNumoFwar(){
        return this._numofwar;
    }

    public void setNumoFwar(double numofwar){
        this._numofwar = numofwar;
    }

    public double getIV(){
        return this._iv;
    }

    public void setIV(double iv){
        this._iv = iv;
    }

    public double getHv20(){
        return this._hv20;
    }

    public void setHv20(double hv20){
        this._hv20 = hv20;
    }

    public double getRsi9(){
        return this._rsi9;
    }

    public void setRsi9(double rsi9){
        this._rsi9 = rsi9;
    }

    public double getRsi14(){
        return this._rsi14;
    }

    public void setRsi14(double rsi14){
        this._rsi14 = rsi14;
    }

    public double getUnderLyHv(){
        return this._underlyhv;
    }

    public void setUnderLyHv(double underlyhv){
        this._underlyhv = underlyhv;
    }

    public double getClose(){
        return this._close;
    }

    public void setClose(double close){
        this._close = close;
    }
}
