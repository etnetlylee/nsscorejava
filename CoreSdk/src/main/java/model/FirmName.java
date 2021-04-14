package model;

public class FirmName {
    String _firmCode;
    String _engName;
    String _scName;
    String _tcName;

    public FirmName(String firmCode, String engName, String scName, String tcName) {
        this._firmCode = firmCode;
        this._engName = engName;
        this._scName = scName;
        this._tcName = tcName;
    }

    public void setFirmCode(String firmCode) {
        this._firmCode = firmCode;
    }

    public String getFirmCode() {
        return this._firmCode;
    }

    public void setEngName(String engName) {
        this._engName = engName;
    }

    public String getEngName() {
        return this._engName;
    }

    public void setScName(String scName) {
        this._scName = scName;
    }

    public String getScName() {
        return this._scName;
    }

    public void setTcName(String tcName) {
        this._tcName = tcName;
    }

    public String getTcName() {
        return this._tcName;
    }

}
