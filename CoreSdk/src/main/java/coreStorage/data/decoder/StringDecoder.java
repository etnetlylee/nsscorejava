package coreStorage.data.decoder;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.QuoteData;
import coreModel.RawData;

public class StringDecoder extends Decoder {
    public static final String uniqueId = "string";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        QuoteData cached = getContext().getStorage().getQuoteData(code, rawData.getFieldID());
        NssData nssData;
        if (cached != null) {
            nssData = cached.getNssData();
        } else {
            nssData = new NssData(null);
        }
        final String value = (rawData.getData()).toString();
        nssData.setData(value);
        nssData.setReady(true);
        return nssData;
    }
}
