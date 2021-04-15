package coreModel;

public class NssData {
    String _fieldId;
    Object _data;
    int _lastUpdate;
    boolean _ready = false;
    boolean _snapshot;
    String _name;

    public NssData(Object data) {
        setData(data);
    }

    public String getName() {
        return this._name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public String getFieldID() {
        return this._fieldId;
    }

    public void setFieldID(String fieldId) {
        this._fieldId = fieldId;
    }

    public Object getData() {
        return this._data;
    }

    public void setData(Object data) {
        this._data = data;
    }

    public int getLastUpdate() {
        return this._lastUpdate;
    }

    public void setLastUpdate(int lastUpdate) {
        this._lastUpdate = lastUpdate;
    }

    public boolean getReady() {
        return this._ready;
    }

    public void setReady(boolean ready) {
        this._ready = ready;
    }

    public boolean getSnapshot() {
        return this._snapshot;
    }

    public void setSnapshot(boolean snapshot) {
        this._snapshot = snapshot;
    }
}
