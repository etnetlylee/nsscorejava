package coreModel;

public class RawData {
    String _code;
    String _clientFieldId;
    String _serverFieldId;
    Object _data; // String | List<String>
    boolean _snapshot;

    public RawData(String code) {
        this._code = code;
    }


    public String getCode() {
        return this._code;
    }

    public void setCode(String code) {
        this._code = code;
    }

    public String getFieldID() {
        return this._clientFieldId;
    }

    public void setFieldID(String fieldID) {
        this._clientFieldId = fieldID;
    }

    public String getServerFieldId() {
        return this._serverFieldId;
    }

    public void setServerFieldId(String fieldId) {
        this._serverFieldId = fieldId;
    }

    public Object getData() {
        return this._data;
    }

    public void setData(Object data) {
        this._data = data;
    }

    public boolean isSnapshot() {
        return this._snapshot;
    }

    public void setSnapshot(boolean snapshot) {
        this._snapshot = snapshot;
    }
}
