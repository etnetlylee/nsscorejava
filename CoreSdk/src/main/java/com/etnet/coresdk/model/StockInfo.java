package com.etnet.coresdk.model;

public class StockInfo {
    String _code;
    String _tcName;
    String _enName;
    String _scName;
    String _pyName;
    String _type;

    boolean _vcm = false;
    boolean _cas = false;
    boolean _pos = false;

    public StockInfo(
            String code,
            String tcName,
            String enName,
            String scName,
            String pyName,
            String type) {
        this._code = code;
        this._tcName = tcName;
        this._enName = enName;
        this._scName = scName;
        this._pyName = pyName;
        this._type = type;
    }

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

    public String getPyName() {
        return this._pyName;
    }

    public void setPyName(String value) {
        this._pyName = value;
    }

    public String getType() {
        return this._type;
    }

    public void setType(String type) {
        this._type = type;
    }

    public boolean isVCM() {
        return this._vcm;
    }

    public void setVCM(boolean value) {
        this._vcm = value;
    }

    public boolean isCAS() {
        return this._cas;
    }

    public void setCAS(boolean value) {
        this._cas = value;
    }

    public boolean isPOS() {
        return this._pos;
    }

    public void setPOS(boolean value) {
        this._pos = value;
    }
}
