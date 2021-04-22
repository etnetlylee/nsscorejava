package coreStorage.model;

import java.util.List;

public class IndustryAnalysis {
    String _indCode;
    int _fundInflow;
    List<RegionPercentage> _region;
    int _turnover;
    String _date;
    int _average;

    public String getIndCode() {
        return this._indCode;
    }

    public void setIndCode(String indCode) {
        this._indCode = indCode;
    }

    public int getFundInflow() {
        return this._fundInflow;
    }

    public void setFundInflow(int fundInflow) {
        this._fundInflow = fundInflow;
    }

    public List<RegionPercentage> getRegion() {
        return this._region;
    }

    public void setRegion(List<RegionPercentage> region) {
        this._region = region;
    }

    public int getTurnover() {
        return this._turnover;
    }

    public void setTurnover(int turnover) {
        this._turnover = turnover;
    }

    public String getDate() {
        return this._date;
    }

    public void setDate(String date) {
        this._date = date;
    }

    public int getAverage() {
        return this._average;
    }

    public void setAverage(int average) {
        this._average = average;
    }
}
