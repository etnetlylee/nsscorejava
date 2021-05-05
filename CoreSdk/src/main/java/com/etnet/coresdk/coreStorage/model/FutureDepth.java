package com.etnet.coresdk.coreStorage.model;

public class FutureDepth {
    String _bidPrice;
    String _bidQty;
    String _askPrice;
    String _askQty;
    String _spreadNo;

    public String getBidPrice() {
        return this._bidPrice;
    }

    public void setBidPrice(String bidPrice) {
        this._bidPrice = bidPrice;
    }

    public String getBidQty() {
        return this._bidQty;
    }

    public void setBidQty(String bidQty) {
        this._bidQty = bidQty;
    }

    public String getAskPrice() {
        return this._askPrice;
    }

    public void setAskPrice(String askPrice) {
        this._askPrice = askPrice;
    }

    public String getAskQty() {
        return this._askQty;
    }

    public void setAskQty(String askQty) {
        this._askQty = askQty;
    }

    public String getSpreadNo() {
        return this._spreadNo;
    }

    public void setSpreadNo(String spreadNo) {
        this._spreadNo = spreadNo;
    }
}
