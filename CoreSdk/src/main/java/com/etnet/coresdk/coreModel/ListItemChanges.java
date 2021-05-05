package com.etnet.coresdk.coreModel;

public class ListItemChanges {
    String _value;
    int _from;
    int _to;

    public ListItemChanges(String value, int from, int to) {
        this._value = value;
        this._from = from;
        this._to = to;
    }

    public String getValue() {
        return this._value;
    }

    public int getFrom() {
        return this._from;
    }

    public int getTo() {
        return this._to;
    }
}
