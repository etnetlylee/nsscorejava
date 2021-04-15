package coreModel;

public class DataState {
    boolean _dataReady;
    boolean _firstNotify;
    int _count;

    public DataState() {
        this._dataReady = false;
        this._firstNotify = true;
        this._count = 1;
    }

    public boolean getDataReady() {
        return this._dataReady;
    }

    public void setDataReady(boolean dataReady) {
        this._dataReady = dataReady;
    }

    public boolean getFirstNotify() {
        return this._firstNotify;
    }

    public void setFirstNotify(boolean firstNotify) {
        this._firstNotify = firstNotify;
    }

    public int getCount() {
        return this._count;
    }

    public void setCount(int count) {
        this._count = count;
    }

}
