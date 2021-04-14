package coreModel;

import java.util.List;

import api.ContextProvider;

abstract class Decoder extends ContextProvider {
    static final String uniqueId = "default";

    NssCoreContext _context;

    @Override
    public void setContext(NssCoreContext context) {
        this._context = context;
    }

    NssCoreContext getContext() {
        return this._context;
    }

    NssData decodeRaw(String code, RawData rawData) {
        return null;
    }

    NssData decodeSnapshot(String code, RawData rawData) {
        return null;
    }

    NssData decodeStream(String code, RawData rawData) {
        return null;
    }

    NssData decodeList(
            String code, String fieldId, List<String> value, boolean fromSnapshot,
            String name) {
        return null;
    }
}
