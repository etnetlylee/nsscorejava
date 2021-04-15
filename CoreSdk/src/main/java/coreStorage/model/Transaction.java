package coreStorage.model;

public class Transaction {
    int _displayTimestamp;
    int _groupTimestamp;
    int _timestamp;
    double _open;
    double _high;
    double _low;
    double _close;
    double _nominal;
    double _volume;
    double _baseVolume;
    String _tranType;
    int _transNo;

    public Transaction(
            int displayTimestamp,
            int groupTimestamp,
            int timestamp,
            double open,
            double high,
            double low,
            double close,
            double nominal,
            double volume,
            double baseVolume,
            String tranType,
            int transNo) {
        this._displayTimestamp = displayTimestamp;
        this._groupTimestamp = groupTimestamp;
        this._timestamp = timestamp;
        this._open = open;
        this._high = high;
        this._low = low;
        this._close = close;
        this._nominal = nominal;
        this._volume = volume;
        this._baseVolume = baseVolume;
        this._tranType = tranType;
        this._transNo = transNo;
    }

    public int getDisplayTimestamp() {
        return this._displayTimestamp;
    }

    public void setDisplayTimestamp(int displayTimestamp) {
        this._displayTimestamp = displayTimestamp;
    }

    public int getGroupTimestamp() {
        return this._groupTimestamp;
    }

    public void setGroupTimestamp(int groupTimestamp) {
        this._groupTimestamp = groupTimestamp;
    }

    public int getTimestamp() {
        return this._timestamp;
    }

    public void setTimestamp(int timestamp) {
        this._timestamp = timestamp;
    }

    public double getOpen() {
        return this._open;
    }

    public void setOpen(double open) {
        this._open = open;
    }

    public double getHigh() {
        return this._high;
    }

    public void setHigh(double high) {
        this._high = high;
    }

    public double getLow() {
        return this._low;
    }

    public void setLow(double low) {
        this._low = low;
    }

    public double getClose() {
        return this._close;
    }

    public void setClose(double close) {
        this._close = close;
    }

    public double getNominal() {
        return this._nominal;
    }

    public void setNominal(double nominal) {
        this._nominal = nominal;
    }

    public double getVolume() {
        return this._volume;
    }

    public void setVolume(double volume) {
        this._volume = volume;
    }

    public double getBaseVolume() {
        return this._baseVolume;
    }

    public void setBaseVolume(double baseVolume) {
        this._baseVolume = baseVolume;
    }

    public String getTranType() {
        return this._tranType;
    }

    public void setTranType(String tranType) {
        this._tranType = tranType;
    }

    public int getTransNo() {
        return this._transNo;
    }

    public void setTransNo(int transNo) {
        this._transNo = transNo;
    }
}
