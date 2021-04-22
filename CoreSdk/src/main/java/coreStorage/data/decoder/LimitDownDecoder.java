package coreStorage.data.decoder;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.QuoteData;
import coreModel.RawData;
import coreStorage.model.LimitUpdownStruct;

public class LimitDownDecoder extends Decoder {
    public static final String uniqueID = "limitdown";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        Double limitDown = null;
        QuoteData cached =
                getContext().getStorage().getQuoteData(code, rawData.getFieldID());
        NssData nssData;
        final String value = (rawData.getData()).toString();

        if (cached != null) {
            nssData = cached.getNssData();
        } else {
            nssData = new NssData(null);
        }

        LimitUpdownStruct limitUDStruct;
        if ((boolean) nssData.getData()) {
            limitUDStruct = (LimitUpdownStruct) nssData.getData();
        } else {
            limitUDStruct = new LimitUpdownStruct();
        }
        String serverFieldId = rawData.getServerFieldId();
        if (serverFieldId == "315") {
            limitUDStruct.setRemindType(value);
        } else if (serverFieldId == "49") {
            limitUDStruct.setPreClose(Double.parseDouble(value));
        } else if (serverFieldId == "118") {
            double rate = 1.0 - Double.parseDouble(value) / 100.00;
            if (rate == Double.NaN) {
                limitUDStruct.setRate(null);
            } else {
                limitUDStruct.setRate(rate);
            }
        }

        if (limitUDStruct.getPreClose() != null &&
                limitUDStruct.getRate() != null &&
                (limitUDStruct.getRemindType() != null && limitUDStruct.getRemindType() == "N")) {
            limitDown = limitUDStruct.getPreClose() * limitUDStruct.getRate();
            limitUDStruct.setLimPrice(limitDown);
        }
        nssData.setData(limitUDStruct);
        if (limitUDStruct.getRemindType() != null &&
                limitUDStruct.getPreClose() != null &&
                limitUDStruct.getRate() != null) {
            nssData.setReady(true);
        }
        return nssData;
    }
}
