package coreModel;

public class AsaDecoder {
    String _mFieldID;
    String _mName;
    String _mUniqueID;
    Decoder _addIfAbsent;

    public AsaDecoder(String fieldId, String name, String uniqueId, Decoder ifAbsent) {
        this._mFieldID = fieldId;
        this._mName = name;
        this._mUniqueID = uniqueId;
        this._addIfAbsent = ifAbsent;
    }

    public String getUniqueId() {
        return this._mUniqueID;
    }

    public String getName() {
        return this._mName;
    }

    public String getFieldID() {
        return this._mFieldID;
    }

    public Decoder ifAbsent() {
        return this._addIfAbsent;
    }

}
