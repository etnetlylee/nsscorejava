package com.etnet.coresdk.decoder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.etnet.coresdk.coreModel.Decoder;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.RawData;
import com.etnet.coresdk.coreStorage.model.AsaStorage;

import static com.etnet.coresdk.constants.AsaConstant.AH_TYPE_SH;
import static com.etnet.coresdk.constants.AsaConstant.AH_TYPE_SZ;
import static com.etnet.coresdk.constants.AsaConstant.STOCK_EXCHANGE_SSE;
import static com.etnet.coresdk.constants.AsaConstant.STOCK_EXCHANGE_SZSE;

public class Decoder4 extends Decoder {
    public static final String uniqueID = "4";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = (rawData.getData()).toString();
        AsaStorage data = getContext().getAsaStorage();
        if (value != null && value.trim() != "") {
            final List<String> values = Arrays.asList(value.split("|"));

            reset(data);

            for (String val : values) {
                final List<String> info = Arrays.asList(val.split(","));
                String hCode = info.get(0);
                String aCode = info.get(1);
                String target = info.get(2);

                if (data.AHMAP.containsKey(hCode)) {
                    data.AHMAP.put(hCode, aCode);
                }

                if (target == STOCK_EXCHANGE_SSE) {
                    if (data.SZORSHMAP.containsKey(aCode)) {
                        data.SZORSHMAP.put(aCode, AH_TYPE_SH);
                    }
                } else if (target == STOCK_EXCHANGE_SZSE) {
                    if (data.SZORSHMAP.containsKey(aCode)) {
                        data.SZORSHMAP.put(aCode, AH_TYPE_SZ);
                    }
                }
            }
        }

        return null;
    }

    void reset(AsaStorage data) {
        data.AHMAP = new HashMap<String, String>();
        data.SZORSHMAP = new HashMap<String, Integer>();
    }
}
