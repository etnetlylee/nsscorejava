package com.etnet.coresdk.events;

public class AppEvent {
    int _event;
    Object _data;

    public AppEvent(int event, Object data) {
        this._event = event;
        this._data = data;
    }

    public int event() {
        return this._event;
    }

    public Object data() {
        return this._data;
    }
}
