package com.etnet.coresdk.coreStorage.data.decoder;

import com.etnet.coresdk.coreModel.Decoder;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.QuoteData;
import com.etnet.coresdk.coreModel.RawData;

public class LongDecoder extends Decoder {
    public static final String uniqueId = "long";

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
        nssData.setData(Double.parseDouble(value));
        nssData.setReady(true);
        return nssData;
    }
}
