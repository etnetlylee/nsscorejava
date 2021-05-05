package com.etnet.coresdk.model;

import java.util.List;

public class Industry {
    String _industryCode;
    String _traName;
    String _simName;
    String _engName;
    List<String> _codeList;

    public String getTraName() {
        return this._traName;
    }

    public void setTraName(String traName) {
        this._traName = traName;
    }

    public String getSimName() {
        return this._simName;
    }

    public void setSimName(String simName) {
        this._simName = simName;
    }

    public String getEngName() {
        return this._engName;
    }

    public void setEngName(String engName) {
        this._engName = engName;
    }

    public List<String> getCodeList() {
        return this._codeList;
    }

    public void setCodeList(List<String> codeList) {
        this._codeList = codeList;
    }

    public String getIndustryCode() {
        return this._industryCode;
    }

    public void setIndustryCode(String industryCode) {
        this._industryCode = industryCode;
    }
}
