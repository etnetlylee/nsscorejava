package com.etnet.coresdk.coreStorage.data.decoder;

import java.util.Arrays;
import java.util.List;

import com.etnet.coresdk.coreModel.Decoder;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.QuoteData;
import com.etnet.coresdk.coreModel.RawData;
import com.etnet.coresdk.coreStorage.model.FutureDepth;
import com.etnet.coresdk.coreStorage.model.FutureDepthMap;

public class FutureDepthDecoder extends Decoder {
    public static final String uniqueID = "futuredepth";

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

        final String value = rawData.getData().toString();
        // Fix empty
        if (value.length() == 0) {
            return nssData;
        }

        final List<String> values = Arrays.asList(value.split("#"));
        FutureDepthMap futureDepthMap = (FutureDepthMap) nssData.getData();
        if (futureDepthMap == null) {
            futureDepthMap = new FutureDepthMap();
        }
        FutureDepthMap futureMap = new FutureDepthMap();
        for (String val : values) {
            FutureDepth futureDepthStruct = new FutureDepth();
            final String spreadNo = val.split("|")[3];
            futureDepthStruct.setSpreadNo(spreadNo);
            final String bidAskTarg = val.split("|")[2];
            final String price = val.split("|")[0];
            final String qty = val.split("|")[1];
            if (bidAskTarg == "A") {
                futureDepthStruct.setAskPrice(price);
                futureDepthStruct.setAskQty(qty);
            } else if (bidAskTarg.length() > 0 && bidAskTarg.indexOf("B") == bidAskTarg.length() - 1
            ) {
                futureDepthStruct.setBidPrice(price);
                futureDepthStruct.setBidQty(qty);
            }
            futureDepthMap.updateMap(spreadNo, futureDepthStruct);
            futureMap.updateMap(spreadNo, futureDepthStruct);
        }


        nssData.setData(futureDepthMap);
        nssData.setReady(true);
        return nssData;
    }
}
