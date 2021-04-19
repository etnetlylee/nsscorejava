package decoder;

import java.util.Arrays;
import java.util.List;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.RawData;
import coreStorage.model.AsaStorage;
import model.StockInfo;

public class Decoder10 extends Decoder {
    static final String uniqueID = "10";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = (rawData.getData()).toString();
        AsaStorage data = getContext().getAsaStorage();
        if (value != null && value.trim() != "") {
            final List<String> values = Arrays.asList(value.split("|"));

            for (String val : values) {
                final List<String> valuess = Arrays.asList(val.split(","));
                final String stockCode = valuess.get(0);
                final String traName = valuess.get(1);
                final String simName = valuess.get(2);
                final String engName = valuess.get(3);
                StockInfo stockInfo = new StockInfo(stockCode, traName, engName, simName, null, null);

                if (valuess.size() == 5) {
                    stockInfo.setPyName(valuess.get(4));
                }
                data.STOCK_INFO_MAP.put(stockCode, stockInfo);
            }
        }
        return null;
    }
}
