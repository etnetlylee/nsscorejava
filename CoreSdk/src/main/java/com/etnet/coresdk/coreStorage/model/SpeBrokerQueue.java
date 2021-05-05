package com.etnet.coresdk.coreStorage.model;

import java.util.ArrayList;
import java.util.List;

public class SpeBrokerQueue {
    List<SpeBroker> _sepBrokerQueue = new ArrayList<SpeBroker>();

    public void add(SpeBroker speBrokerStruct) {
        this._sepBrokerQueue.add(speBrokerStruct);
    }

    public List<SpeBroker> getSpeBrokerQueueStruct() {
        return this._sepBrokerQueue;
    }
}
