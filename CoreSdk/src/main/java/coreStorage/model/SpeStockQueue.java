package coreStorage.model;

import java.util.ArrayList;
import java.util.List;

public class SpeStockQueue {
    List<SpeStock> _speCodeQueue = new ArrayList<SpeStock>();

    public void add(SpeStock speCodeStruct) {
        this._speCodeQueue.add(speCodeStruct);
    }

    public List<SpeStock> getSpeCodeQueueStruct() {
        return this._speCodeQueue;
    }
}
