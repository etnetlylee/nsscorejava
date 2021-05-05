package com.etnet.coresdk.model;

import java.util.List;

public class IEInfoStruct {
    String _enUrl;
    String _tcUrl;
    String _scUrl;
    List<String> _targetList;

    public IEInfoStruct(List<String> links) {
        setEnUrl(links.get(1));
        if (links.size() < 3) {
            setTcUrl(links.get(1));
            setScUrl(links.get(1));
        } else {
            setTcUrl(links.get(2));
            setScUrl(links.get(3));
        }

        if (links.size() > 4) {
            for (int j = 4; j < links.size(); j++) {
                this._targetList.add(links.get(j));
            }
        }
    }

    public String getEnUrl() {
        return this._enUrl;
    }

    public void setEnUrl(String enUrl) {
        this._enUrl = enUrl;
    }

    public String getTcUrl() {
        return this._tcUrl;
    }

    public void setTcUrl(String tcUrl) {
        this._tcUrl = tcUrl;
    }

    public String getScUrl() {
        return this._scUrl;
    }

    public void setScUrl(String scUrl) {
        this._scUrl = scUrl;
    }

    public List<String> getTargetList() {
        return this._targetList;
    }

    public void setTargetList(List<String> targetList) {
        this._targetList = targetList;
    }
}
