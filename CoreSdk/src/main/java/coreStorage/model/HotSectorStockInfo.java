package coreStorage.model;

import java.util.List;

public class HotSectorStockInfo {
    String _id;
    String _engName;
    String _tcName;
    String _scName;
    List<String> _codeList;

    public String getId() {
        return this._id;
    }

    public void setId(String id) {
        this._id = id;
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

    public List<String> getCodeList() {
        return this._codeList;
    }

    public void setCodeList(List<String> codeList) {
        this._codeList = codeList;
    }
}
