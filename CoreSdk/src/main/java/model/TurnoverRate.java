package model;

public class TurnoverRate {
    int _volume;
    float _loatISC;
    float _turnoverRate;

    public TurnoverRate(int volume, float loatISC, float rate) {
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

    public float getLoatISC() {
        return this._loatISC;
    }

    public void setTurnoverRate(float rate) {
        this._turnoverRate = rate;
    }

    public float getTurnoverRate() {
        return this._turnoverRate;
    }
}
