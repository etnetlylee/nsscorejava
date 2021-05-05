package com.etnet.coresdk.model;

public class Disclaimer {
    String _tc;
    String _sc;
    String _en;

    public Disclaimer(
            String tc,
            String sc,
            String en
    ) {}

    public String getTc() {
        return this._tc;
    }

    public void setTc(String value) {
        this._tc = value;
    }

    public String getSc() {
        return this._sc;
    }

    public void setSc(String value) {
        this._sc = value;
    }

    public String getEn() {
        return this._en;
    }

    public void setEn(String value) {
        this._en = value;
    }
}
