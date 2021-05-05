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
import com.etnet.coresdk.coreStorage.model.HotSectorStockInfo;

public class HotSectorDecoder extends Decoder {
    public static final String uniqueID = "hotsector";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        QuoteData cached = getContext().getStorage().getQuoteData(code, rawData.getFieldID());
        NssData nssData;
        final String value = (rawData.getData()).toString();
        final List<String> data = Arrays.asList(value.split("|"));

        Map<String, HotSectorStockInfo> hotSectorMap = new HashMap<String, HotSectorStockInfo>();
        for (String val : data) {
            if (val != null && val.length() > 0) {
                final List<String> splitData = Arrays.asList(val.split(","));
                final String id = splitData.get(0);
                final String enName = splitData.get(1);
                final String tcName = splitData.get(2);
                final String scName = splitData.get(3);
                final List<String> codeList = Arrays.asList(splitData.get(4).split(" "));
                List<String> validCodeList = new ArrayList<String>();
                HotSectorStockInfo hotSectorStockInfo = new HotSectorStockInfo();
                validCodeList.addAll(codeList);

                hotSectorStockInfo.setId(id);
                hotSectorStockInfo.setEngName(enName);
                hotSectorStockInfo.setTcName(tcName);
                hotSectorStockInfo.setScName(scName);

                hotSectorStockInfo.setCodeList(validCodeList);
                hotSectorMap.put(id, hotSectorStockInfo);
            }
        }

        if (cached != null) {
            nssData = cached.getNssData();
        } else {
            nssData = new NssData(null);
        }
        nssData.setData(hotSectorMap);
        nssData.setReady(true);
        return nssData;
    }
}
