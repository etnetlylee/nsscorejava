package coreStorage.model;

public class VcmTriggerEvent {
    int _seqNo;
    String _startTime;
    String _endTime;
    double _referencePrice;
    double _lowerPriceLimit;
    double _upperPriceLimit;
    boolean _indicator = false; // false means INDICATOR_OFF; default is false;

    public int getSeqNo() {
        return this._seqNo;
    }

    public void setSeqNo(int seqNo) {
        this._seqNo = seqNo;
    }

    public String getStartTime() {
        return this._startTime;
    }

    public void setStartTime(String startTime) {
        this. _startTime = startTime;
        if (_startTime == null) {
            this._startTime = "";
        }
    }

    public String getEndTime() {
        return this._endTime;
    }

    public void setEndTime(String endTime) {
        this._endTime = endTime;
    }

    public double getReferencePrice() {
        return this._referencePrice;
    }

    public void setReferencePrice(double refPrice) {
        this._referencePrice = refPrice;
    }

    public double getLowerPriceLimit() {
        return this._lowerPriceLimit;
    }

    public void setLowerPriceLimit(double lowPrice) {
        this._lowerPriceLimit = lowPrice;
    }

    public double getUpperPriceLimit() {
        return this._upperPriceLimit;
    }

    public void setUpperPriceLimit(double upperPrice) {
        this._upperPriceLimit = upperPrice;
    }

    public boolean getIndicator() {
        return this._indicator;
    }

    public void setIndicator(boolean indicator) {
        this._indicator = indicator;
    }
}
