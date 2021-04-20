package coreStorage.data.decoder;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.QuoteData;
import coreModel.RawData;

public class CodeDecoder extends Decoder {
    public static final String uniqueID = "code";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        QuoteData cached = getContext().getStorage().getQuoteData(code, rawData.getFieldID());
        NssData nssData;

        if (cached != null) {
            nssData = cached.getNssData();
        } else {
            nssData = new NssData(null);
        }
        nssData.setData(code);
        nssData.setReady(true);
        return nssData;
    }
}
