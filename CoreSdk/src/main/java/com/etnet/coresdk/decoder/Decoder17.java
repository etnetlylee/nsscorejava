package com.etnet.coresdk.decoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.etnet.coresdk.coreModel.Decoder;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.RawData;
import com.etnet.coresdk.coreStorage.model.AsaStorage;

public class Decoder17 extends Decoder {
    public static final String uniqueID = "17";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = (rawData.getData()).toString();
        AsaStorage data = getContext().getAsaStorage();
        data.TRADING_DAY_LIST = new ArrayList<Integer>();

        if (value != null && value.trim() != "") {
            final List<String> values = Arrays.asList(value.split("|"));
            for (String val : values){
                data.TRADING_DAY_LIST.add(Integer.parseInt(val, 10));
            }
        }
        return null;
    }
}
