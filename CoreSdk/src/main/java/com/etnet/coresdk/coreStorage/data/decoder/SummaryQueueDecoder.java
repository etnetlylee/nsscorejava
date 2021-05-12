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

public class SummaryQueueDecoder extends Decoder {
    public static final String uniqueID = "summaryQueue";

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
        final List<String> bidAsk = Arrays.asList(value.split("#"));

        Map<String, List<Map>> summary = new HashMap<String, List<Map>>() {{
            put("bidQueue", new ArrayList<Map>());
            put("askQueue", new ArrayList<Map>());
        }};
        if (nssData != null && nssData.getData() != null) {
            summary = (Map<String, List<Map>>) nssData.getData();
        }

        for (String val : bidAsk) {
            List<String> data = Arrays.asList(val.split("|"));
            String pos = "";
            String no = "";
            String vol = "";
            List<String> bidAskItem = Arrays.asList(vol, no);
            String bidAskFlag = "";

            if (data != null && data.size() == 4) {
                // fieldId = 241
                // 65000|5|A|0
                bidAskFlag = data.get(2);
                pos = data.get(3);
                no = data.get(1);
                vol = data.get(0);
                bidAskItem = Arrays.asList(vol, no);
            } else {
                // 65K  5|A|0
                if (data != null) {
                    bidAskFlag = (data.size() > 1) ? data.get(1) : "";
                    pos = (data.size() > 2) ? data.get(2) : "";
                    if (data.get(0) != null && data.get(0) != "") {
                        no = data.get(0).substring(data.get(0).length() - 3);
                        vol = data.get(0).substring(0, data.get(0).length() - 3);
                    } else {
                        no = "";
                        vol = "";
                    }
                    bidAskItem = Arrays.asList(vol, no);
                }
            }


            if (pos != null && pos != "") {
                List<Map> list =
                        (bidAskFlag == "A") ? summary.get("askQueue") : summary.get("bidQueue");
                List<String> finalBidAskItem = bidAskItem;
                Map<String, String> _temp = new HashMap<String, String>() {{
                    put("no", finalBidAskItem.get(1));
                    put("volume", finalBidAskItem.get(0));
                }};

                addOrUpdateList(list, Integer.parseInt(pos), _temp);
            }
        }

        nssData.setData(summary);
        nssData.setReady(true);
        return nssData;
    }

    public void addOrUpdateList(List<Map> list, int pos, Map data) {
        if (list != null) {
            if (pos > list.size()) {
                list.add(data);
            } else {
                list.add(pos, data);
            }
        }
    }

    public void removeSpread(List<Map> list, int pos) {
        if (list != null) {
            if (pos > list.size()) {
                list.add(new HashMap());
            } else {
                list.add(pos, new HashMap());
            }
        }
    }
}
