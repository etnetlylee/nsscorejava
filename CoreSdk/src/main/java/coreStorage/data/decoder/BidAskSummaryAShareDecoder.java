package coreStorage.data.decoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.QuoteData;
import coreModel.RawData;

public class BidAskSummaryAShareDecoder extends Decoder {
    public static final String uniqueID = "bidasksummaryashare";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        QuoteData cached = getContext().getStorage().getQuoteData(code, rawData.getFieldID());
        NssData nssData;
        if (cached != null) {
            nssData = cached.getNssData();
        } else {
            nssData = new NssData(null);
        }

        Map<String, Map> bidAskSummaryMap = new HashMap<String, Map>() {{
            put("bidQueue", new HashMap<>());
            put("askQueue", new HashMap<>());
        }};
        if (nssData.getData() != null) {
            bidAskSummaryMap = (Map<String, Map>) nssData.getData();
        }

        final String value = (rawData.getData()).toString();
        final List<String> values = Arrays.asList(value.split("#"));
        for (String data : values) {
            final List<String> datas = Arrays.asList(data.split("|"));
            if (datas.size() >= 4) {
                Map<String, String> bidAskSummaryStruct = new HashMap<String, String>() {{
                    put("spreadNo", null);
                    put("no", null);
                    put("volume", null);
                    put("price", null);
                }};
                if (datas.get(3) != null && datas.get(3).trim().length() > 0) {
                    // spread no is not blank or null.
                    String price = "";
                    if (datas.size() == 5) {
                        price = datas.get(4);
                    }
                    bidAskSummaryStruct.put("no", datas.get(0));
                    bidAskSummaryStruct.put("volume", datas.get(1));
                    bidAskSummaryStruct.put("price", price);

                    int spreadNo = Integer.parseInt(datas.get(3), 10);
                    bidAskSummaryStruct.put("spreadNo", String.valueOf(spreadNo));

                    if (datas.get(2).trim() == "A") {
                        bidAskSummaryMap.get("askQueue").put(spreadNo, bidAskSummaryStruct);
                    } else {
                        bidAskSummaryMap.get("bidQueue").put(spreadNo, bidAskSummaryStruct);
                    }
                }
            }
        }

        nssData.setData(bidAskSummaryMap);
        nssData.setReady(true);
        return nssData;
    }
}
