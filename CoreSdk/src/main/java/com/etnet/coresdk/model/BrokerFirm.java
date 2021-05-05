package com.etnet.coresdk.model;

import java.util.ArrayList;
import java.util.List;

public class BrokerFirm {
    String _firmCode = "0";
    String _engName = "";
    String _tcName = "";
    String _scName = "";
    String _region = "";
    List<String> _brokersNo = new ArrayList();

    public String getFirmCode() {
        return this._firmCode;
    }


    public void setFirmCode(String firmCode) {
        this._firmCode = firmCode;
    }

    public String getEngName() {
        return this._engName;
    }

    public void setEngName(String engName) {
        this._engName = engName;
    }

    public String getTcName() {
        return this._tcName;
    }

    public void setTcName(String tcName) {
        this._tcName = tcName;
    }

    public String getScName() {
        return this._scName;
    }

    public void setScName(String scName) {
        this._scName = scName;
    }

    public String getRegion() {
        return this._region;
    }

    public void setRegion(String region) {
        this._region = region;
    }

    public List<String> getBrokersNo() {
        return this._brokersNo;
    }

    public void setBrokersNo(List<String> brokersNo) {
        _brokersNo = brokersNo;
    }

}
