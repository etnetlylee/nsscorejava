package com.etnet.coresdk.model;

public class TurnoverRate {
    int _volume;
    double _loatISC;
    double _turnoverRate;

    public TurnoverRate(Integer volume, Double loatISC, Double rate) {
        this._volume = volume;
        this._loatISC = loatISC;
        this._turnoverRate = rate;
    }

    public void setVolume(int volume) {
        this._volume = volume;
    }

    public int getVolume() {
        return this._volume;
    }

    public void setLoatISC(float loatISC) {
        this._loatISC = loatISC;
    }

    public double getLoatISC() {
        return this._loatISC;
    }

    public void setTurnoverRate(double rate) {
        this._turnoverRate = rate;
    }

    public double getTurnoverRate() {
        return this._turnoverRate;
    }
}
