package com.etnet.coresdk.model;

import java.util.List;

public class IndustryInfo {
    String _code;
    String _tcName;
    String _enName;
    String _scName;
    List<String> _codeList;

    public String getCode() {
        return this._code;
    }

    public void setCode(String value) {
        this._code = value;
    }

    public String getTcName() {
        return this._tcName;
    }

    public void setTcName(String value) {
        this._tcName = value;
    }

    public String getEnName() {
        return this._enName;
    }

    public void setEnName(String value) {
        this._enName = value;
    }

    public String getScName() {
        return this._scName;
    }

    public void setScName(String value) {
        this._scName = value;
    }

    public List<String> getCodeList() {
        return this._codeList;
    }

    public void setCodeList(List<String> value) {
        this._codeList = value;
    }
}
