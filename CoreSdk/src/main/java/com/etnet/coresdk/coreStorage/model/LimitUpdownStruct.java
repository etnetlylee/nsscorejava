package com.etnet.coresdk.coreStorage.model;

public class LimitUpdownStruct {
    String _remindType;
    Double _preClose;
    Double _rate;
    Double _limPrice;

    public String getRemindType() {
        return this._remindType;
    }

    public void setRemindType(String remindType) {
        this._remindType = remindType;
    }

    public Double getPreClose() {
        return this._preClose;
    }

    public void setPreClose(Double preClose) {
        this._preClose = preClose;
    }

    public Double getRate() {
        return this._rate;
    }

    public void setRate(Double rate) {
        this._rate = rate;
    }

    public Double getLimPrice() {
        return this._limPrice;
    }

    public void setLimPrice(Double limPrice) {
        this._limPrice = limPrice;
    }

}
