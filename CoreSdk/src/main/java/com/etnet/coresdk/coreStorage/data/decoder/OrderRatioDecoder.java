package com.etnet.coresdk.coreStorage.data.decoder;

import java.util.Arrays;
import java.util.List;

import com.etnet.coresdk.coreModel.Decoder;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.QuoteData;
import com.etnet.coresdk.coreModel.RawData;
import com.etnet.coresdk.coreStorage.model.BidAskSummary;
import com.etnet.coresdk.coreStorage.model.BidAskSummaryMap;
import com.etnet.coresdk.coreStorage.model.OrderRatio;

public class OrderRatioDecoder extends Decoder {
    static final String uniqueID = "orderratio";

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

        OrderRatio orderRatioStruct =
                (boolean)(nssData.getData()) ? (OrderRatio) nssData.getData() : new OrderRatio(3);
        // nssData.setName(name);
        nssData.setFieldID(rawData.getFieldID());

        if (orderRatioStruct.getBidAskSummaryMap() == null) {
            orderRatioStruct.setBidAskSummaryMap(new BidAskSummaryMap());
        }

        final List<String> values = Arrays.asList(value.split("#"));

        for (String data: values){
            final List<String> datas = Arrays.asList(data.split("|"));
            if (datas.size() == 4) {
                BidAskSummary bidAskSummaryStruct = new BidAskSummary();
                if (datas.get(3) != null && datas.get(3).length() > 0) {
                    // spread no is not blank or null.
                    final String volume = datas.get(0);
                    final String num = datas.get(1);

                    String bidAskFlag = datas.get(2).trim();
                    if (bidAskFlag == "B") {
                        bidAskSummaryStruct.setBidNumber(num);
                        bidAskSummaryStruct.setBidVolume(volume);
                    } else if (bidAskFlag == "A") {
                        bidAskSummaryStruct.setAskNumber(num);
                        bidAskSummaryStruct.setAskVolume(volume);
                    }

                    String spreadNoString = datas.get(3);
                    int spreadNo = Integer.parseInt(spreadNoString, 10);

                    bidAskSummaryStruct.setSpreadNo(spreadNo);
                    orderRatioStruct
                            .getBidAskSummaryMap()
                            .updateMap(spreadNo, bidAskSummaryStruct);
                }
            }
        }

        orderRatioStruct.calculateOrderRatio();

        nssData.setData(orderRatioStruct);
        nssData.setReady(true);
        return nssData;
    }
}
