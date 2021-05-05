package com.etnet.coresdk.model;

public class SearchListItem {
    String _code;
    String _type;
    String _aliasedCode;

    public SearchListItem(String code, String type, String aliasedCode) {}

    public String getCode() {
        return this._code;
    }

    public void setCode(String value) {
        this._code = value;
    }

    public String getType() {
        return this._type;
    }

    public void setType(String value) {
        this._type = value;
    }

    public String getAliasedCode() {
        return this._aliasedCode;
    }

    public void setAliasedCode(String value) {
        this._aliasedCode = value;
    }
}
