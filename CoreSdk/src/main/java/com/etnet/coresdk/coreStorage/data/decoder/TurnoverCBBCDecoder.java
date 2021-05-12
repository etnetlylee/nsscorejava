package com.etnet.coresdk.coreStorage.data.decoder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.etnet.coresdk.coreModel.Decoder;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.QuoteData;
import com.etnet.coresdk.coreModel.RawData;
import com.etnet.coresdk.util.DataHelper;

public class TurnoverCBBCDecoder extends Decoder {
    public static final String uniqueID = "turnovercbbc";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        QuoteData cached =
                getContext().getStorage().getQuoteData(code, rawData.getFieldID());
        NssData nssData;
        final String value = (rawData.getData()).toString();

        if (cached != null) {
            nssData = cached.getNssData();
        } else {
            nssData = new NssData(null);
        }

        if (value == null || value.trim().length() == 0) {
            return nssData;
        }

        final List<String> values = Arrays.asList(value.split("|"));
        Map<String, Double> turnoverCBBCStruct = new HashMap<String, Double>() {{
            put("bullTurnover", null);
            put("bearTurnover", null);
            put("allTurnover", null);
            put("bullPercent", null);
            put("bearPercent", null);
        }};

        if (values != null && values.size() == 2 && values.get(0).trim().length() > 0) {
            double call = Double.parseDouble(values.get(1));
            double put = Double.parseDouble(values.get(0));
            double total = call + put;

            turnoverCBBCStruct.put("bullTurnover", call);
            turnoverCBBCStruct.put("bearTurnover", put);
            turnoverCBBCStruct.put("allTurnover", total);

            double callPer = 0;
            double putPer = 0;
            if (total != 0) {
                callPer = call / total;
                putPer = put / total;
            }

            double callF = DataHelper.formatNumber(callPer);
            double putF = DataHelper.formatNumber(putPer);

            turnoverCBBCStruct.put("bullPercent", callF);
            turnoverCBBCStruct.put("bearPercent", putF);
        }

        nssData.setData(turnoverCBBCStruct);
        nssData.setReady(true);

        return nssData;
    }
}
