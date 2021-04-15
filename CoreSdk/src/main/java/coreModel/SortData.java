package coreModel;

public class SortData extends DataSubscription{
    int _updateCount;
    SortCodes _sortCodes;
    int _fullListSize;

    public SortData(int seqNo) {
        super(seqNo);
        this._updateCount = -1;
        this._sortCodes = new SortCodes(null);
    }

    public boolean isFirstUpdate() {
        return this._updateCount == 1;
    }

    public SortCodes getSortCodes() {
        return this._sortCodes;
    }

    public void setSortCodes(SortCodes sortCodes) {
        this._updateCount++;
        this._sortCodes = sortCodes;
    }

    public int getFullListSize() {
        return this._fullListSize;
    }

    public void setFullListSize(int fullListSize) {
        this._fullListSize = fullListSize;
    }
}
