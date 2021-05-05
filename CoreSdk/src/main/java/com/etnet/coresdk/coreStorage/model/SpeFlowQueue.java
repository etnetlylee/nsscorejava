package com.etnet.coresdk.coreStorage.model;

import java.util.ArrayList;
import java.util.List;

public class SpeFlowQueue {
    List<SpeFlow> sepFlowQueue = new ArrayList<SpeFlow>();

    public void add(SpeFlow sepFlowStruct) {
        this.sepFlowQueue.add(sepFlowStruct);
    }

    List<SpeFlow> getSepFlowQueue() {
        return this.sepFlowQueue;
    }
}
