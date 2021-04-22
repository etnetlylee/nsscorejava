package coreStorage.data.decoder;

import java.util.Arrays;
import java.util.List;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.QuoteData;
import coreModel.RawData;
import coreStorage.model.BidAskSummary;
import coreStorage.model.BidAskSummaryMap;
import coreStorage.model.OrderRatio;

public class OrderRatioAHDecoder extends Decoder {
    public static final String uniqueID = "orderratioah";

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
                ((Boolean) nssData.getData()) ? (OrderRatio) nssData.getData() : new OrderRatio(5);
        nssData.setFieldID(rawData.getFieldID());

        if (orderRatioStruct.getBidAskSummaryMap() == null) {
            orderRatioStruct.setBidAskSummaryMap(new BidAskSummaryMap());
        }

        final List<String> values = Arrays.asList(value.split("#"));
        for (String data : values){
            final List<String> datas = Arrays.asList(data.split("|"));
            if (datas.size() == 5) {
                BidAskSummary bidAskSummaryStruct = new BidAskSummary();

                if (datas.get(3) != null && datas.get(3).length() > 0) {
                    // spread no is not blank or null.
                    String volume = datas.get(1);
                    String bidAskFlag = datas.get(2).trim();
                    String price = datas.get(4);

                    if (bidAskFlag == "B") {
                        bidAskSummaryStruct.setBidVolume(volume);
                        bidAskSummaryStruct.setBidPrice(price);
                    } else if (bidAskFlag == "A") {
                        bidAskSummaryStruct.setAskVolume(volume);
                        bidAskSummaryStruct.setAskPrice(price);
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
