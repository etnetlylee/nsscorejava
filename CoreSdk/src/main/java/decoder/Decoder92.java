package decoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.RawData;
import coreStorage.model.AsaStorage;
import model.StockInfo;

public class Decoder92 extends Decoder {
    static final String uniqueID = "92";

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
                    final List<String> eligibleList = Arrays.asList(infos.get(1).split("|"));

                    StockInfo stockInfo = data.STOCK_INFO_MAP.get(stockCode);
                    if (stockInfo == null) {
                        stockInfo = new StockInfo(stockCode, null, null, null, null, null);
                    }
                    data.STOCK_INFO_MAP.put(stockCode, stockInfo);
                    // reset all in case broadcast update
                    if (stockInfo.isVCM()) {
                        stockInfo.setVCM(false);
                    }
                    if (stockInfo.isCAS()) {
                        stockInfo.setCAS(false);
                    }
                    if (stockInfo.isPOS()) {
                        stockInfo.setPOS(false);
                    }
                    // apply new status
                    for (String status : eligibleList){
                        if (status == "1") {
                            stockInfo.setVCM(true);
                        } else if (status == "2") {
                            stockInfo.setCAS(true);
                        } else if (status == "3") {
                            stockInfo.setPOS(true);
                        }
                    }
                }
            }
        }
        return null;
    }
}
