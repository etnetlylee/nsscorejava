package com.etnet.coresdk.coreStorage.data.decoder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.etnet.coresdk.coreModel.Decoder;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.QuoteData;
import com.etnet.coresdk.coreModel.RawData;
import com.etnet.coresdk.util.DataHelper;

public class TurnoverWarrDecoder extends Decoder {
    public static final String uniqueID = "turnoverwarr";

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
        Map<String, Double> turnoverWarrStruct = new HashMap<String, Double>() {{
            put("callTurnover", null);
            put("putTurnover", null);
            put("allTurnover", null);
            put("callPercent", null);
            put("putPercent", null);
        }};
        if (values.size() == 2 && values.get(0).trim().length() > 0) {
            String callString = values.get(1);
            String putString = values.get(0);
            double call = 0.00;
            double put = 0.00;

            if (Pattern.compile("[0-9]+").matcher(callString).matches() &&
                    Pattern.compile("[0-9]").matcher(putString).matches()) {
                call = Double.parseDouble(callString);
                put = Double.parseDouble(putString);
            } else {
                if (callString.contains(".") || putString.contains(".")) {
                    // FIXME strange logic?
                    call = Double.parseDouble(callString.split(".")[0]);
                    put = Double.parseDouble(putString.split(".")[0]);
                }
            }

            double total = call + put;
            turnoverWarrStruct.put("callTurnover", call);
            turnoverWarrStruct.put("putTurnover", put);
            turnoverWarrStruct.put("allTurnover", total);

            double callPer = 0.00;
            double putPer = 0.00;
            if (total != 0) {
                callPer = call / total;
                putPer = put / total;
            }
            double callF = DataHelper.formatNumber(callPer);
            double putF = DataHelper.formatNumber(putPer);

            turnoverWarrStruct.put("callPercent", callF);
            turnoverWarrStruct.put("putPercent", putF);
        }

        nssData.setData(turnoverWarrStruct);
        nssData.setReady(true);

        return nssData;
    }
}
