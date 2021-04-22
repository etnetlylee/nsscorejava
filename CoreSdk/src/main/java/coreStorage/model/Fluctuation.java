package coreStorage.model;

public class Fluctuation {
    Double _dayHigh;
    Double _dayLow;
    Double _prevousClose;
    Double _fluctuation;

    public void calculateFluctuation() {
        Double result = null;
        if ((_dayHigh != null) && (_dayLow != null)) {
            if (_prevousClose != null) {
                if (_dayHigh > _prevousClose && _dayLow > _prevousClose) {
                    _dayLow = _prevousClose;
                } else if (_dayHigh < _prevousClose && _dayLow < _prevousClose) {
                    _dayHigh = _prevousClose;
                }
            }
            if ((_dayHigh + _dayLow) / 2 != 0) {
                result = ((_dayHigh - _dayLow) / ((_dayHigh + _dayLow) / 2)) * 100;
            }
        }
        setFluctuation(result);
    }

    public Double getDayHigh() {
        return this._dayHigh;
    }

    public void setDayHigh(Double dayHigh) {
        this._dayHigh = dayHigh;
    }

    public Double getDayLow() {
        return this._dayLow;
    }

    public void setDayLow(Double dayLow) {
        this._dayLow = dayLow;
    }

    public Double getPrevousClose() {
        return this._prevousClose;
    }

    public void setPrevousClose(Double prevousClose) {
        this._prevousClose = prevousClose;
    }

    public Double getFluctuation() {
        return this._fluctuation;
    }

    public void setFluctuation(Double fluctuation) {
        this._fluctuation = fluctuation;
    }
}
