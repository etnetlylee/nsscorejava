package com.etnet.coresdk.events;

public class NssTime {
    int _serverTime;
    int _drift;

    public NssTime(int serverTime, int drift) {
        this._serverTime = serverTime;
        this._drift = drift;
    }

    public void setServerTime(int serverTime) {
        this._serverTime = serverTime;
    }

    public int getServerTime() {
        return this._serverTime;
    }

    public void setDrift(int drift) {
        this._drift = drift;
    }

    public int getDrift() {
        return this._drift;
    }

}
