package com.etnet.coresdk.decoder;

import java.util.Arrays;
import java.util.List;

import com.etnet.coresdk.coreModel.Decoder;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.RawData;
import com.etnet.coresdk.coreStorage.model.AsaStorage;
import com.etnet.coresdk.model.FirmName;

public class Decoder87 extends Decoder {
    public static final String uniqueID = "87";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = (rawData.getData()).toString();
        AsaStorage data = getContext().getAsaStorage();
        if (value != null && value.trim() != "") {
            final List<String> values = Arrays.asList(value.split("|"));

            for (String broker : values) {
                final List<String> infos = Arrays.asList(broker.split(","));

                final String firmCode = infos.get(0);
                final String engName = infos.get(1);
                final String scName = infos.get(1);
                final String tcName = infos.get(2);

                data.FIRM_NAME.add(new FirmName(
                        firmCode,
                        engName,
                        scName,
                        tcName
                ));
            }
        }

        return null;
    }
}
