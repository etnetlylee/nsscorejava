package com.etnet.coresdk.decoder;

import java.util.Arrays;
import java.util.List;

import com.etnet.coresdk.coreModel.Decoder;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.RawData;

import static com.etnet.coresdk.constants.SecurityType.SECURITYTYPE_ETF;

public class Decoder19 extends Decoder {
    public static final String uniqueID = "19";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = (rawData.getData()).toString();
        if (value != null && value.trim() != "") {
            final List<String> etfList = Arrays.asList(value.split("|"));
            for (String v : etfList) {
                getContext().getAsaStorage().STKTYPEMAP.put(v, SECURITYTYPE_ETF);
            }
        }

        return null;
    }
}
