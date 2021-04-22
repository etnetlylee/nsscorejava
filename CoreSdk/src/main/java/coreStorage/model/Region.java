package coreStorage.model;

import java.util.ArrayList;
import java.util.List;

public class Region {
    List<Double> _regionQueue = new ArrayList<Double>();

    public void add(Double region) {
        this._regionQueue.add(region);
    }

    public List<Double> getRegionStruct() {
        return this._regionQueue;
    }
}
