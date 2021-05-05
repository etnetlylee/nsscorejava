package com.etnet.coresdk.coreModel;

import java.util.ArrayList;
import java.util.List;

public class DataSubscription {
    int _seqNo;
    List<Subscription> _subscription = new ArrayList<Subscription>();
    int _compositeDepsCount = 0;

    DataSubscription(int seqNo) {
        this._seqNo = seqNo;
        this._compositeDepsCount = 0;
    }

    public int getSeqNo() {
        return this._seqNo;
    }

    public int getCompositeDepsCount() {
        return this._compositeDepsCount;
    }

    public int plusCompositeDepsCount() {
        return ++this._compositeDepsCount;
    }

    public int minusCompositeDepsCount() {
        return --this._compositeDepsCount;
    }

    public List<Subscription> getSubscription() {
        return this._subscription;
    }
}
