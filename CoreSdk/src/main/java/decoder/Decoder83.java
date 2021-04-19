package decoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.RawData;
import coreStorage.model.AsaStorage;
import model.StockInfo;

public class Decoder83 extends Decoder {
    static final String uniqueID = "83";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = (rawData.getData()).toString();
        AsaStorage data = getContext().getAsaStorage();
        if (value != null && value.trim() != "") {
            final List<String> values = Arrays.asList(value.split(","));
            for (String record : values) {
                final List<String> infos = Arrays.asList(record.split(":"));

                if (infos != null && infos.size() >= 2) {
                    final String stockCode = infos.get(0);
                    final String stockType = infos.get(1);
                    if (Pattern.compile("[0-9]+").matcher(stockType).matches()) {
                        final int iType = Integer.parseInt(stockType, 10);

                        StockInfo stockInfo = data.STOCK_INFO_MAP.get(stockCode) != null
                                ? data.STOCK_INFO_MAP.get(stockCode)
                                : new StockInfo(stockCode, null, null, null, null, null);

                        data.STOCK_INFO_MAP.put(stockCode, stockInfo);

                        // TODO:
                        // stockInfo.setVCM((iType && 1) != 0); // value 1 (VCM eligable)
                        // stockInfo.setCAS((iType && 2) != 0); // value 2 (CAS eligable)
                    }
                }
            }
        }
        return null;
    }
}
