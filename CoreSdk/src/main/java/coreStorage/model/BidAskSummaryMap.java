package coreStorage.model;

import java.util.HashMap;
import java.util.Map;

public class BidAskSummaryMap {
    Map<Integer, BidAskSummary> _bidAskSummaryMap = new HashMap<Integer, BidAskSummary>();

    public void updateMap(int spreadNo, BidAskSummary updateData) {
        BidAskSummary summary = _bidAskSummaryMap.get(spreadNo);
        if (summary == null) {
            summary = new BidAskSummary();
            _bidAskSummaryMap.put(spreadNo,summary );
        }
        if (updateData.getSpreadNo() != 0.0) {
            summary.setSpreadNo(updateData.getSpreadNo());
        }
        if (updateData.getAskVolume() != null) {
            summary.setAskVolume(updateData.getAskVolume());
        }
        if (updateData.getAskNumber() != null) {
            summary.setAskNumber(updateData.getAskNumber());
        }
        if (updateData.getBidVolume() != null) {
            summary.setBidVolume(updateData.getBidVolume());
        }
        if (updateData.getBidNumber() != null) {
            summary.setBidNumber(updateData.getBidNumber());
        }
        if (updateData.getBidPrice() != null) {
            summary.setBidPrice(updateData.getBidPrice());
        }
        if (updateData.getAskPrice() != null) {
            summary.setAskPrice(updateData.getAskPrice());
        }
    }

    public void addToMap(int spreadNo, BidAskSummary bidAskSummaryStruct) {
        this._bidAskSummaryMap.put(spreadNo, bidAskSummaryStruct);
    }

    public Map<Integer, BidAskSummary> getMap() {
        return this._bidAskSummaryMap;
    }

    public BidAskSummary getStruct(int spreadNo) {
        return _bidAskSummaryMap.get(spreadNo);
    }

    public int size() {
        return _bidAskSummaryMap.size();
    }
}
