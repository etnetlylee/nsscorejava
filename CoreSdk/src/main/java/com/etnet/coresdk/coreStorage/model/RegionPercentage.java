package com.etnet.coresdk.coreStorage.model;

public class RegionPercentage {
    String _regionCode;
    String _value;

    public RegionPercentage(String regionCode, String value) {
        this._regionCode = regionCode;
        this._value = value;
    }

    public String getRegionCode() {
        return this._regionCode;
    }

    public void setRegionCode(String regionCode) {
        this._regionCode = regionCode;
    }

    public String getValue() {
        return this._value;
    }

    public void setValue(String value) {
        this._value = value;
    }
}
