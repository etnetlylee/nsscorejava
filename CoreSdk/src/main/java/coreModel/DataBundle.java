package coreModel;

import java.util.Map;

public class DataBundle {
    String _code;
    Map<String, String> fields;

    public DataBundle(String code) {
        this._code = code;
    }

    public void add(String fieldId, String value) {
    }
}
