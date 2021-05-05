package com.etnet.coresdk.decoder;

import java.util.Arrays;
import java.util.List;

import com.etnet.coresdk.coreModel.Decoder;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.RawData;
import com.etnet.coresdk.coreStorage.model.AsaStorage;

public class Decoder39 extends Decoder {
    static final String uniqueID = "39";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = (rawData.getData()).toString();
        AsaStorage data = getContext().getAsaStorage();
        if (value != null && value.trim() != "") {
            final List<String> ahft = Arrays.asList(value.split("#"));
            for (String info : ahft) {
                final List<String> infos = Arrays.asList(info.split("|"));
                if (infos.size() >= 3) {
                    final String tStock = infos.get(0);
                    final String t1Stock = infos.get(1);

                    data.AHFT_MAP.put(tStock, t1Stock);
                    data.AHFT_VALUE_MAP.put(t1Stock, tStock);
                }
            }
        }

        return null;
    }
}
