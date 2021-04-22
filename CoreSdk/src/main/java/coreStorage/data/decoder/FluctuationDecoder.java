package coreStorage.data.decoder;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.QuoteData;
import coreModel.RawData;
import coreStorage.model.Fluctuation;

public class FluctuationDecoder extends Decoder {
    public static final String uniqueID = "fluctuation";

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
        Fluctuation flucStruct =
                (nssData.getData() != null) ? (Fluctuation) nssData.getData() : new Fluctuation();
        final String serverFieldId = rawData.getServerFieldId();
        if (serverFieldId == "41") {
            // high
            flucStruct.setDayHigh(Double.parseDouble(value));
        } else if (serverFieldId == "42") {
            // low
            flucStruct.setDayLow(Double.parseDouble(value));
        } else if (serverFieldId == "49") {
            // close
            flucStruct.setPrevousClose(Double.parseDouble(value));
        }

        flucStruct.calculateFluctuation();

        nssData.setData(flucStruct);
        if (flucStruct.getFluctuation() != null) {
            nssData.setReady(true);
        }

        return nssData;
    }
}