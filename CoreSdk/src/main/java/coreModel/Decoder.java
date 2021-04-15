package coreModel;

import java.util.List;

import api.ContextProvider;

public class Decoder extends ContextProvider {
    static final String uniqueId = "default";

    NssCoreContext _context;

    @Override
    public void setContext(NssCoreContext context) {
        this._context = context;
    }

    public NssCoreContext getContext() {
        return this._context;
    }

    public NssData decodeRaw(String code, RawData rawData) {
        return null;
    }

    public NssData decodeSnapshot(String code, RawData rawData) {
        return null;
    }

    public NssData decodeStream(String code, RawData rawData) {
        return null;
    }

    public NssData decodeList(
            String code, String fieldId, List<String> value, boolean fromSnapshot,
            String name) {
        return null;
    }
}
