package coreStorage.model;

import java.util.ArrayList;
import java.util.List;

public class IndustryTrace {
    List<IndustryAnalysis> _anaQueue = new ArrayList<IndustryAnalysis>();
    int _indNumber = 0;

    public void add(IndustryAnalysis anaStruct) {
        this._anaQueue.add(anaStruct);
    }

    public List<IndustryAnalysis> getAnaQueue() {
        return this._anaQueue;
    }

    public void setIndustryCount(int indNumber) {
        this._indNumber = indNumber;
    }

    public int getIndustryCount() {
        return this._indNumber;
    }
}
