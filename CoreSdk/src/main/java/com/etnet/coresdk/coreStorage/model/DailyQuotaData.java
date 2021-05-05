package com.etnet.coresdk.coreStorage.model;

public class DailyQuotaData {
    int _date;
    int _timestamp;
    double _balance;
    double _totalSum;
    double _buyTurnover;
    double _buyNumber;
    double _askTurnover;
    double _askNumber;
    double _localTurnover;
    double _forwardCombinedQuota;
    double _forwardCapitalFlow;
    double _forwardTotalTurnover;
    double _forwardLocalTurnover;

    public DailyQuotaData(
            int date,
            int timestamp,
            double balance,
            double totalSum,
            double buyTurnover,
            double buyNumber,
            double askTurnover,
            double askNumber,
            double localTurnover,
            double forwardCombinedQuota,
            double forwardCapitalFlow,
            double forwardTotalTurnover,
            double forwardLocalTurnover) {
        this._date = date;
        this._timestamp = timestamp;
        this._balance = balance;
        this._totalSum = totalSum;
        this._buyTurnover = buyTurnover;
        this._buyNumber = buyNumber;
        this._askTurnover = askTurnover;
        this._askNumber = askNumber;
        this._localTurnover = localTurnover;
        this._forwardCombinedQuota = forwardCombinedQuota;
        this._forwardCapitalFlow = forwardCapitalFlow;
        this._forwardTotalTurnover = forwardTotalTurnover;
        this._forwardLocalTurnover = forwardLocalTurnover;
    }

    public int getDate(){
        return this._date;
    }

    public void setDate(int date) {
        this._date = date;
    }

    public int getTimestamp(){
        return this._timestamp;
    }

    public void setTimestamp(int timestamp) {
        this._timestamp = timestamp;
    }

    public double getBalance(){
        return this._balance;
    }

    public void setBalance(double balance) {
        this._balance = balance;
    }

    public double getTotalSum(){
        return this._totalSum;
    }

    public void setTotalSum(double totalSum) {
        this._totalSum = totalSum;
    }

    public double getBuyTurnover(){
        return this._buyTurnover;
    }

    public void setBuyTurnover(double buyTurnover) {
        this._buyTurnover = buyTurnover;
    }

    public double getBuyNumber(){
        return this._buyNumber;
    }

    public void setBuyNumber(double buyNumber) {
        this._buyNumber = buyNumber;
    }

    public double getAskTurnover(){
        return this._askTurnover;
    }

    public void setAskTurnover(double askTurnover) {
        this._askTurnover = askTurnover;
    }

    public double getAskNumber(){
        return this._askNumber;
    }

    public void setAskNumber(double askNumber) {
        this._askNumber = askNumber;
    }

    public double getLocalTurnover(){
        return this._localTurnover;
    }

    public void setLocalTurnover(double localTurnover) {
        this._localTurnover = localTurnover;
    }

    public double getForwardCombinedQuota(){
        return this._forwardCombinedQuota;
    }

    public void setForwardCombinedQuota(double forwardCombinedQuota) {
        this._forwardCombinedQuota = forwardCombinedQuota;
    }

    public double getForwardCapitalFlow(){
        return this._forwardCapitalFlow;
    }

    public void setForwardCapitalFlow(double forwardCapitalFlow) {
        this._forwardCapitalFlow = forwardCapitalFlow;
    }

    public double getForwardTotalTurnover(){
        return this._forwardTotalTurnover;
    }

    public void setForwardTotalTurnover(double forwardTotalTurnover) {
        this._forwardTotalTurnover = forwardTotalTurnover;
    }

    public double getForwardLocalTurnover(){
        return this._forwardLocalTurnover;
    }

    public void setForwardLocalTurnover(double forwardLocalTurnover) {
        this._forwardLocalTurnover = forwardLocalTurnover;
    }
}
