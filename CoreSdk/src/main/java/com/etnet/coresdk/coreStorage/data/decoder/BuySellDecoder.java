package com.etnet.coresdk.coreStorage.data.decoder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.etnet.coresdk.coreModel.Decoder;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.QuoteData;
import com.etnet.coresdk.coreModel.RawData;

public class BuySellDecoder extends Decoder {
    public static final String uniqueID = "buysell";
    
    @Override
    public NssData decodeStream(String code, RawData rawData) {
        QuoteData cached = getContext().getStorage().getQuoteData(code, rawData.getFieldID());
        NssData nssData;

        final String value = (rawData.getData()).toString();
        final List<String> data = Arrays.asList(value.split("|"));
        Map<String, Double> newValue= new HashMap<String, Double>();
        if (data != null && data.size() == 4) {
            final double bidAM = Double.parseDouble(data.get(0));
            final double askAM = Double.parseDouble(data.get(1));
            final double bidPM = Double.parseDouble(data.get(2));
            final double askPM = Double.parseDouble(data.get(3));

            final double total = bidAM + askAM + bidPM + askPM;
            final double bidTotal = bidAM + bidPM;
            final double askTotal = askAM + askPM;

            double bidPer = 0.0;
            double askPer = 0.0;
            if (total != 0) {
                bidPer = bidTotal / total;
                askPer = askTotal / total;
            }
            final double bidPerF = (bidPer);
            final double askPerF = (askPer);

            double bidAMPer = 0.0;
            double askAMPer = 0.0;
            double bidPMPer = 0.0;
            double askPMPer = 0.0;

            if (bidTotal != 0) {
                bidAMPer = bidAM / bidTotal;
                bidPMPer = bidPM / bidTotal;
            }
            final double bidAMPerF = (bidAMPer);
            final double bidPMPerF = (bidPMPer);

            if (askTotal != 0) {
                askAMPer = askAM / askTotal;
                askPMPer = askPM / askTotal;
            }
            final double askAMPerF = (askAMPer);
            final double askPMPerF = (askPMPer);

            newValue.put("capitalFlow", askTotal - bidTotal);
            newValue.put("capitalInFlow", askTotal);
            newValue.put("capitalOutFlow", bidTotal);
            newValue.put("bidPer", askPerF);
            newValue.put("askPer", bidPerF);
            newValue.put("bidAMPer", askAMPerF);
            newValue.put("askAMPer", bidAMPerF);
            newValue.put("bidPMPer", askPMPerF);
            newValue.put("askPMPer", bidPMPerF);
        }

        if (cached != null) {
            nssData = cached.getNssData();
        } else {
            nssData = new NssData(null);
        }
        nssData.setData(newValue);
        nssData.setReady(true);
        return nssData;
    }
}
