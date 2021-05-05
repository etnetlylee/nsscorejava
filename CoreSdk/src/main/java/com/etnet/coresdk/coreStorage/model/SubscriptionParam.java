package com.etnet.coresdk.coreStorage.model;

public class SubscriptionParam {
    String _code;
    String _period;
    boolean _tradingDayOnly;
    int _range;
    boolean _snapshot;

    public SubscriptionParam(
    String code,
        String period,
        boolean tradingDayOnly,
        int range,
        boolean snapshot) {
        this._code = code;
        this._period = period;
        this._tradingDayOnly = tradingDayOnly;
        this._range = range;
        this._snapshot = snapshot;
    }

    public String getCode(){
        return this._code;
    }

    public String getPeriod(){
        return this._period;
    }

    public boolean getTradingDayOnly(){
        return this._tradingDayOnly;
    }

    public int getRange(){
        return this._range;
    }

    public boolean getSnapshot(){
        return this._snapshot;
    }
}
