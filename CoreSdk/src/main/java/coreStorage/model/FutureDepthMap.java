package coreStorage.model;

import java.util.ArrayList;
import java.util.List;

public class FutureDepthMap {
    List<FutureDepth> _futureMap = new ArrayList<FutureDepth>();

    public void updateMap(String spreadNo, FutureDepth updateData) {
        int index = Integer.parseInt(spreadNo, 10);
        FutureDepth future = this._futureMap.get(index);
        if (future == null) {
            future = new FutureDepth();
            this._futureMap.set(index, future);
        }
        if (updateData.getAskPrice() != null) {
            future.setAskPrice(updateData.getAskPrice());
        }
        if (updateData.getAskQty() != null) {
            future.setAskQty(updateData.getAskQty());
        }
        if (updateData.getBidPrice() != null) {
            future.setBidPrice(updateData.getBidPrice());
        }
        if (updateData.getBidQty() != null) {
            future.setBidQty(updateData.getBidQty());
        }
    }

    public void addToMap(String spreadNo, FutureDepth futureDepthStruct) {
        int index = Integer.parseInt(spreadNo, 10);
        this._futureMap.set(index, futureDepthStruct);
    }

    public List<FutureDepth> getMap() {
        return this._futureMap;
    }

    public FutureDepth getStruct(String spreadNo) {
        int index = Integer.parseInt(spreadNo,10);
        return this._futureMap.get(index);
    }

    public int size() {
        return this._futureMap.size();
    }
}
