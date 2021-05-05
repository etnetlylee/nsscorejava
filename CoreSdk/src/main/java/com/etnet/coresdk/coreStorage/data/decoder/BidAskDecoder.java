package com.etnet.coresdk.coreStorage.data.decoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.etnet.coresdk.coreModel.Decoder;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.QuoteData;
import com.etnet.coresdk.coreModel.RawData;

public class BidAskDecoder extends Decoder {
    public static final String uniqueID = "bidAsk";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        QuoteData cached = getContext().getStorage().getQuoteData(code, rawData.getFieldID());
        NssData nssData;

        if (cached != null) {
            nssData = cached.getNssData();
        } else {
            nssData = new NssData(null);
        }

        final String value = (rawData.getData()).toString();
        if (value == "") {
            nssData.setData(null);
        } else {
            final List<String> data = Arrays.asList(value.split("|"));
            final double bidValue = Double.parseDouble(data.get(1));
            final double askValue = Double.parseDouble(data.get(0));
            final double total = askValue + bidValue;
            final double bidPerRaw = bidValue / total * 100;
            Map<String, List<Map>> summary = new HashMap<String, List<Map>>() {{
                put("bidQueue", new ArrayList<Map>());
                put("askQueue", new ArrayList<Map>());
            }};
            final Map<String, Double> newValue = new HashMap<String, Double>() {{
                put("bidPerRaw", bidPerRaw);
                put("askPerRaw", 100 - bidPerRaw);
                put("askPer", Double.valueOf(Math.round(askValue / total * 100)));
                put("bidPer", Double.valueOf(Math.round(bidPerRaw)));
            }};
            nssData.setData(newValue);
        }

        nssData.setReady(true);
        return nssData;
    }
}
