package coreModel;

import java.util.List;

public class DecoderInfo {
    String _fieldId;
    List<String> _realIds;
    String _name;
    String _uniqueId;
    Decoder _ifAbsent;
    boolean _composite;

    public DecoderInfo(String fieldId, List<String> realIds, String name,
                String uniqueId, Decoder ifAbsent) {
        this._fieldId = fieldId;
        if (realIds != null){
            this._realIds = realIds;
        }
        this._name = name;
        this._ifAbsent = ifAbsent;
        this._uniqueId = uniqueId;
        this._composite = (!_realIds.isEmpty() || fieldId != _realIds.get(0));
    }

    public String getUniqueId() {
        return this._uniqueId;
    }

    public String getFieldId() {
        return this._fieldId;
    }

    public List<String> getServerFieldIds() {
        return this._realIds;
    }

    public String getName() {
        return this._name;
    }

    public Decoder ifAbsent() {
        return this._ifAbsent;
    }

    public boolean isComposite() {
        return this._composite;
    }
}
