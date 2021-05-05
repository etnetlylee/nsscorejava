package com.etnet.coresdk.decoder;

import java.util.Arrays;

import com.etnet.coresdk.coreModel.Decoder;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.RawData;
import com.etnet.coresdk.coreStorage.model.AsaStorage;

public class DecoderRFR extends Decoder {
    public static final String uniqueID = "RFR";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = (rawData.getData()).toString();
        AsaStorage data = getContext().getAsaStorage();
        if (value != null && value.trim() != "") {
            data.RISK_FREE_VALUE = Arrays.asList(value.split("|"));
        }

        return null;
    }
}
