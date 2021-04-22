package coreStorage.model;

public class BidAskSummary {
    double _spreadNo = 0.0;
    String _askVolume = null;
    String _askNumber = null;
    String _askPrice = null;
    String _bidVolume = null;
    String _bidNumber = null;
    String _bidPrice = null;

    public double getSpreadNo() {
        return this._spreadNo;
    }

    public void setSpreadNo(double spreadNo) {
        this._spreadNo = spreadNo;
    }

    public String getAskVolume() {
        return this._askVolume;
    }

    public void setAskVolume(String askVolume) {
        this._askVolume = askVolume;
    }

    public String getAskNumber() {
        return this._askNumber;
    }

    public void setAskNumber(String askNumber) {
        this._askNumber = askNumber;
    }

    public String getBidVolume() {
        return this._bidVolume;
    }

    public void setBidVolume(String bidVolume) {
        this._bidVolume = bidVolume;
    }

    public String getBidNumber() {
        return this._bidNumber;
    }

    public void setBidNumber(String bidNumber) {
        this._bidNumber = bidNumber;
    }

    public String getAskPrice() {
        return this._askPrice;
    }

    public void setAskPrice(String askPrice) {
        this._askPrice = _askPrice;
    }

    public String getBidPrice() {
        return this._bidPrice;
    }

    public void setBidPrice(String bidPrice) {
        this._bidPrice = bidPrice;
    }
}
