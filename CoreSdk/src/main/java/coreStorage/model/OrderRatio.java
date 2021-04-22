package coreStorage.model;

import java.util.Map;

import util.DataHelper;

public class OrderRatio {
    BidAskSummaryMap _bidAskSummaryMap;
    int _orderRatio;
    int _orderNum;

    public OrderRatio(int orderNum) {
        _orderNum = orderNum; // calc the 3 best bid/ask position
    }

    public void calculateOrderRatio() {
        double value = 0.0;
        if (_bidAskSummaryMap != null) {
            Map<Integer, BidAskSummary> map = _bidAskSummaryMap.getMap();

            BidAskSummary struct;
            double bidTotalVolume = 0.0;
            double askTotalVolume = 0.0;

            for (int i = 0; i < _orderNum; i++) {
                struct = map.get(i);
                if (struct != null) {
                    if (struct.getBidVolume() != null) {
                        bidTotalVolume += DataHelper.stringToDouble(struct.getBidVolume());
                    }
                    if (struct.getAskVolume() != null) {
                        askTotalVolume += DataHelper.stringToDouble(struct.getAskVolume());
                    }
                }
            }

            if ((bidTotalVolume + askTotalVolume) != 0) {
                value = (bidTotalVolume - askTotalVolume) / (bidTotalVolume + askTotalVolume) * 100;
            }
        }
        setOrderRatio((int) value);
    }

    public BidAskSummaryMap getBidAskSummaryMap() {
        return _bidAskSummaryMap;
    }

    public void setBidAskSummaryMap(BidAskSummaryMap bidAskSummaryMap) {
        _bidAskSummaryMap = bidAskSummaryMap;
    }

    public int getOrderRatio() {
        return _orderRatio;
    }

    public void setOrderRatio(int orderRatio) {
        _orderRatio = orderRatio;
    }
}
