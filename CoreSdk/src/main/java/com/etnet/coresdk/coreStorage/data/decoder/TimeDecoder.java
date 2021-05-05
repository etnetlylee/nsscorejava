package com.etnet.coresdk.coreStorage.data.decoder;

import java.util.Arrays;
import java.util.List;

import com.etnet.coresdk.coreModel.Decoder;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.QuoteData;
import com.etnet.coresdk.coreModel.RawData;

public class TimeDecoder extends Decoder {
    public static final String uniqueID = "time";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        QuoteData cached =
                getContext().getStorage().getQuoteData(code, rawData.getFieldID());
        NssData nssData;
        final String value = (rawData.getData()).toString();
        final List<String> values = Arrays.asList(value.split(":"));

        String newTimeFormat = "";
        if (values.size() == 2) {
            final int hour = Integer.parseInt(values.get(0),10);
            final int min = Integer.parseInt(values.get(1), 10);

            if (hour < 10) {
                newTimeFormat = "0" + hour + ":";
            } else {
                newTimeFormat = "" + hour + ":";
            }

            if (min < 10) {
                newTimeFormat += "0" + min;
            } else {
                newTimeFormat += "" + min;
            }
        }

        if (cached != null) {
            nssData = cached.getNssData();
        } else {
            nssData = new NssData(null);
        }
        nssData.setData(newTimeFormat);
        nssData.setReady(true);
        return nssData;
    }
}
