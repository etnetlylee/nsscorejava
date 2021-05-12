package com.etnet.coresdk.coreStorage.data.decoder;

import java.util.Arrays;
import java.util.List;

import com.etnet.coresdk.coreModel.Decoder;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.QuoteData;
import com.etnet.coresdk.coreModel.RawData;
import com.etnet.coresdk.coreStorage.model.PriceUpdown;

public class PriceUpDownDecoder extends Decoder {
    public static final String uniqueID = "priceUpDown";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        QuoteData cached =
                getContext().getStorage().getQuoteData(code, rawData.getFieldID());
        NssData nssData;
        final String value = (rawData.getData()).toString();
        final List<String> values = Arrays.asList(value.split("|"));

        PriceUpdown priceUpDown = new PriceUpdown(null, null,null,null);

        priceUpDown.setUp(Double.parseDouble(values.get(0)));
        priceUpDown.setNoChange(Double.parseDouble(values.get(1)));
        priceUpDown.setDown(Double.parseDouble(values.get(2)));
        if (values != null && values.size() == 4) {
            priceUpDown.setNoTurnover(Double.parseDouble(values.get(3)));
        }

        if (cached != null) {
            nssData = cached.getNssData();
        } else {
            nssData = new NssData(null);
        }
        nssData.setData(priceUpDown);
        nssData.setReady(true);
        return nssData;
    }
}
