package com.etnet.coresdk.coreStorage.model;

public class MonthlyQuotaData {
    int _date;
    int _timestamp;
    double _buyTurnover;
    double _buyAvgTurnover;
    double _buyNumber;
    double _buyAvgNumber;
    double _askTurnover;
    double _askAvgTurnover;
    double _askNumber;
    double _askAvgNumber;
    double _totalAvgTurnover;
    double _totalNumber;
    double _totalAvgNumber;
    double _forwardCapitalFlow;
    double _forwardTotalTurnover;
    double _forwardLocalTurnover;

    public MonthlyQuotaData(
            int date,
            int timestamp,
            double buyTurnover,
            double buyAvgTurnover,
            double buyNumber,
            double buyAvgNumber,
            double askTurnover,
            double askAvgTurnover,
            double askNumber,
            double askAvgNumber,
            double totalAvgTurnover,
            double totalNumber,
            double totalAvgNumber,
            double forwardCapitalFlow,
            double forwardTotalTurnover,
            double forwardLocalTurnover) {
        this._date = date;
        this._timestamp = timestamp;
        this._buyTurnover = buyTurnover;
        this._buyAvgTurnover = buyAvgTurnover;
        this._buyNumber = buyNumber;
        this._buyAvgNumber = buyAvgNumber;
        this._askTurnover = askTurnover;
        this._askAvgTurnover = askAvgTurnover;
        this._askNumber = askNumber;
        this._askAvgNumber = askAvgNumber;
        this._totalAvgTurnover = totalAvgTurnover;
        this._totalNumber = totalNumber;
        this._totalAvgNumber = totalAvgNumber;
        this._forwardCapitalFlow = forwardCapitalFlow;
        this._forwardTotalTurnover = forwardTotalTurnover;
        this._forwardLocalTurnover = forwardLocalTurnover;
    }

    public int getDate() {
        return this._date;
    }

    public void setDate(int date) {
        this._date = date;
    }

    public int getTimeStamp() {
        return this._timestamp;
    }

    public void setTimeStamp(int timestamp) {
        this._timestamp = timestamp;
    }

    public double getBuyTurnover() {
        return this._buyTurnover;
    }

    public void setBuyTurnover(double buyTurnover) {
        this._buyTurnover = buyTurnover;
    }

    public double getBuyAvgTurnover() {
        return this._buyAvgTurnover;
    }

    public void setBuyAvgTurnover(double buyAvgTurnover) {
        this._buyAvgTurnover = buyAvgTurnover;
    }

    public double getBuyNumber() {
        return this._buyNumber;
    }

    public void setBuyNumber(double buyNumber) {
        this._buyNumber = buyNumber;
    }

    public double getBuyAvgNumber() {
        return this._buyAvgNumber;
    }

    public void setBuyAvgNumber(double buyAvgNumber) {
        this._buyAvgNumber = buyAvgNumber;
    }

    public double getAskTurnover() {
        return this._askTurnover;
    }

    public void setAskTurnover(double askTurnover) {
        this._askTurnover = askTurnover;
    }

    public double getAskAvgTurnover() {
        return this._askAvgTurnover;
    }

    public void setAskAvgTurnover(double askAvgTurnover) {
        this._askAvgTurnover = askAvgTurnover;
    }

    public double getAskNumber() {
        return this._askNumber;
    }

    public void setAskNumber(double askNumber) {
        this._askNumber = askNumber;
    }

    public double getAskAvgNumber() {
        return this._askAvgNumber;
    }

    public void setAskAvgNumber(double askAvgNumber) {
        this._askAvgNumber = askAvgNumber;
    }

    public double getTotalAvgTurnover() {
        return this._totalAvgTurnover;
    }

    public void setTotalAvgTurnover(double totalAvgTurnover) {
        this._totalAvgTurnover = totalAvgTurnover;
    }

    public double getTotalNumber() {
        return this._totalNumber;
    }

    public void setTotalNumber(double totalNumber) {
        this._totalNumber = totalNumber;
    }

    public double getTotalAvgNumber() {
        return this._totalAvgNumber;
    }

    public void setTotalAvgNumber(double totalAvgNumber) {
        this._totalAvgNumber = totalAvgNumber;
    }

    public double getForwardCapitalFlow() {
        return this._forwardCapitalFlow;
    }

    public void setForwardCapitalFlow(double forwardCapitalFlow) {
        this._forwardCapitalFlow = forwardCapitalFlow;
    }

    public double getForwardTotalTurnover() {
        return this._forwardTotalTurnover;
    }

    public void setForwardTotalTurnover(double forwardTotalTurnover) {
        this._forwardTotalTurnover = forwardTotalTurnover;
    }

    public double getForwardLocalTurnover() {
        return this._forwardLocalTurnover;
    }

    public void setForwardLocalTurnover(double forwardCapitalFlow) {
        this._forwardLocalTurnover = forwardCapitalFlow;
    }
}
