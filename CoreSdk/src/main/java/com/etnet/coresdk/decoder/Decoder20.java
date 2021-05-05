package com.etnet.coresdk.decoder;

import com.etnet.coresdk.coreModel.Decoder;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.RawData;

public class Decoder20 extends Decoder {
    static final String uniqueID = "20";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = (rawData.getData()).toString();
        if (value != null) {
            // TODO: ???
            // getContext().getAsaStorage()[code] = value;
        }
        return null;
    }
}
