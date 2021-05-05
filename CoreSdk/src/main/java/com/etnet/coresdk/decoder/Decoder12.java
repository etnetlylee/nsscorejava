package com.etnet.coresdk.decoder;

import com.etnet.coresdk.coreModel.Decoder;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.RawData;
import com.etnet.coresdk.coreStorage.model.AsaStorage;

public class Decoder12 extends Decoder {
    public static final String uniqueID = "12";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = (rawData.getData()).toString();
        AsaStorage data = getContext().getAsaStorage();
        if (value != null && value.trim() != "") {
            data.BLOCKTRADE = Double.parseDouble(value);
            data.BLOCKTRADENUM = data.BLOCKTRADE * 1000;
            data.BLOCKTRADENUM_EX = data.BLOCKTRADE;
        }
        return null;
    }
}
