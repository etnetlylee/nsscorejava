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
import model.TradingTimeInfo;

import static constants.AsaConstant.TRADING_TIME_TYPE_FOR_STOCK;

public class Decoder18 extends Decoder {
    public static final String uniqueID = "18";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = (rawData.getData()).toString();
        AsaStorage data = getContext().getAsaStorage();
        if (value != null && value.trim() != "") {
            data.TRADING_TIME_MAP = new HashMap<String, Map<String, TradingTimeInfo>>();
            final List<String> values = Arrays.asList(value.split("|"));
            // MDF,DEFAULT,930,1200,1230,1300,1600
            // ETNET,UPG_MTS,845,1200,,1230,1630
            for (int i = 0; i < values.size(); i++) {
                TradingTimeInfo tradingTimeInfo = new TradingTimeInfo();
                final List<String> valuesDetail = Arrays.asList(values.get(i).split(","));

                if (valuesDetail.size() != 7) {
                    continue;
                }
                final String typeKey = valuesDetail.get(0);
                final String codeKey = valuesDetail.get(1);
                if (valuesDetail.get(2) != "") {
                    tradingTimeInfo.setAmOpen(valuesDetail.get(2));
                }
                if (valuesDetail.get(3) != "") {
                    tradingTimeInfo.setAmClose(valuesDetail.get(3));
                }
                if (valuesDetail.get(4) != "") {
                    tradingTimeInfo.setCutOff(valuesDetail.get(4));
                }
                if (valuesDetail.get(5) != "") {
                    tradingTimeInfo.setPmOpen(valuesDetail.get(5));
                }
                if (valuesDetail.get(6) != "") {
                    tradingTimeInfo.setPmClose(valuesDetail.get(6));
                }
                if (data.TRADING_TIME_MAP.get(typeKey) == null) {
                    data.TRADING_TIME_MAP.put(typeKey, new HashMap<String, TradingTimeInfo>());
                }
                data.TRADING_TIME_MAP.get(typeKey).put(codeKey, tradingTimeInfo);
                // tradingTimeInfo.$cutOff can be undefined
                if (typeKey == TRADING_TIME_TYPE_FOR_STOCK &&
                        tradingTimeInfo.getCutOff() != null &&
                        tradingTimeInfo.getCutOff() != "") {
                    data.STOCK_DEF_CUTOFF = tradingTimeInfo.getCutOff();
                }
            }
        }
        return null;
    }
}
