package com.etnet.coresdk.coreStorage.data.decoder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.etnet.coresdk.coreModel.Decoder;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.QuoteData;
import com.etnet.coresdk.coreModel.RawData;

public class BidAskQtyDecoder extends Decoder {
    public static final String uniqueID = "bidaskqty";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        QuoteData cached =
                getContext().getStorage().getQuoteData(code, rawData.getFieldID());
        NssData nssData;
        if (cached != null) {
            nssData = cached.getNssData();
        } else {
            nssData = new NssData(null);
        }

        final String value = (rawData.getData()).toString();
        final List<String> values = Arrays.asList(value.split("|"));
        Map<String, String> bidAskQtyStruct = new HashMap<String, String>() {{
            put("bid", null);
            put("ask", null);
        }};
        Map<String, String> ca = (Map<String, String>) cached;
        if (ca != null) {
            bidAskQtyStruct = ca;
        }

        if (values != null && values.size() == 2) {
            bidAskQtyStruct.put("bid", values.get(1));
            bidAskQtyStruct.put("ask", values.get(0));
        }
        nssData.setData(bidAskQtyStruct);
        nssData.setReady(true);
        return nssData;
    }
}
