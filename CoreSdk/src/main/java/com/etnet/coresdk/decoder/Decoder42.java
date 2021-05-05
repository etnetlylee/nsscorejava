package com.etnet.coresdk.decoder;

import java.util.Arrays;
import java.util.List;

import com.etnet.coresdk.coreModel.Decoder;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.RawData;
import com.etnet.coresdk.coreStorage.model.AsaStorage;

public class Decoder42 extends Decoder {
    static final String uniqueID = "42";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = (rawData.getData()).toString();
        AsaStorage data = getContext().getAsaStorage();
        if (value != null && value.trim() != "") {
            final List<String> values = Arrays.asList(value.split("\\$"));
            for (String val : values) {
                final List<String> info = Arrays.asList(val.split(":"));
                final String v = info.get(0);
                final List<String> keys = Arrays.asList(info.get(1).split("|"));
                data.DECI_RELATED_MAP_FROM_ASA.put(v, keys);
                for (String key : keys) {
                    data.OPTION_UNDERLYING_MAP_FROM_ASA.put(key, v);
                }
            }

        }

        return null;
    }
}
