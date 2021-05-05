package com.etnet.coresdk.model;

public class InstrumentInfo {
    String _code;
    String _nameTChi;
    String _nameTChiShort;
    String _nameSChi;
    String _nameSChiShort;
    String _nameEng;
    String _nameEngShort;

    public InstrumentInfo(
            String code,
            String nameTChi,
            String nameTChiShort,
            String nameSChi,
            String nameSChiShort,
            String nameEng,
            String nameEngShort) {
    }

    public String getCode() {
        return this._code;
    }

    public void setCode(String code) {
        this._code = code;
    }

    public String getNameTChi() {
        return this._nameTChi;
    }

    public void setNameTChi(String nameTChi) {
        this._nameTChi = nameTChi;
    }

    public String getNameTChiShort() {
        return this._nameTChiShort;
    }

    public void setNameTChiShort(String nameTChiShort) {
        this._nameTChiShort = nameTChiShort;
    }

    public String getNameSChi() {
        return this._nameSChi;
    }

    public void setNameSChi(String nameSChi) {
        this._nameSChi = nameSChi;
    }

    public String getNameSChiShort() {
        return this._nameSChiShort;
    }

    public void setNameSChiShort(String nameSChiShort) {
        this._nameSChiShort = nameSChiShort;
    }

    public String getNameEng() {
        return this._nameEng;
    }

    public void setNameEng(String nameEng) {
        this._nameEng = nameEng;
    }

    public String getNameEngShort() {
        return this._nameEngShort;
    }

    public void setNameEngShort(String nameEngShort) {
        this._nameEngShort = nameEngShort;
    }
}
