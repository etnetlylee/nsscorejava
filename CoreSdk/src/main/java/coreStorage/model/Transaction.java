package coreStorage.model;

public class Transaction {
    Integer _displayTimestamp;
    Integer _groupTimestamp;
    Integer _timestamp;
    Double _open;
    Double _high;
    Double _low;
    Double _close;
    Double _nominal;
    Double _volume;
    Double _baseVolume;
    String _tranType;
    Integer _transNo;

    public Transaction(
            Integer displayTimestamp,
            Integer groupTimestamp,
            Integer timestamp,
            Double open,
            Double high,
            Double low,
            Double close,
            Double nominal,
            Double volume,
            Double baseVolume,
            String tranType,
            Integer transNo) {
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

    public Integer getDisplayTimestamp() {
        return this._displayTimestamp;
    }

    public void setDisplayTimestamp(Integer displayTimestamp) {
        this._displayTimestamp = displayTimestamp;
    }

    public Integer getGroupTimestamp() {
        return this._groupTimestamp;
    }

    public void setGroupTimestamp(Integer groupTimestamp) {
        this._groupTimestamp = groupTimestamp;
    }

    public Integer getTimestamp() {
        return this._timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this._timestamp = timestamp;
    }

    public Double getOpen() {
        return this._open;
    }

    public void setOpen(Double open) {
        this._open = open;
    }

    public Double getHigh() {
        return this._high;
    }

    public void setHigh(Double high) {
        this._high = high;
    }

    public Double getLow() {
        return this._low;
    }

    public void setLow(Double low) {
        this._low = low;
    }

    public Double getClose() {
        return this._close;
    }

    public void setClose(Double close) {
        this._close = close;
    }

    public Double getNominal() {
        return this._nominal;
    }

    public void setNominal(Double nominal) {
        this._nominal = nominal;
    }

    public Double getVolume() {
        return this._volume;
    }

    public void setVolume(Double volume) {
        this._volume = volume;
    }

    public Double getBaseVolume() {
        return this._baseVolume;
    }

    public void setBaseVolume(Double baseVolume) {
        this._baseVolume = baseVolume;
    }

    public String getTranType() {
        return this._tranType;
    }

    public void setTranType(String tranType) {
        this._tranType = tranType;
    }

    public Integer getTransNo() {
        return this._transNo;
    }

    public void setTransNo(Integer transNo) {
        this._transNo = transNo;
    }
}
