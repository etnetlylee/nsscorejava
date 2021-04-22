package coreStorage.data.decoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.RawData;
import util.AsaDataHelper;
import util.SecurityCodeHelper;

public class TickChartDecoder extends Decoder {
    public static final String uniqueID = "tickchart";
    final Logger log = Logger.getLogger("TickChartDecoder");

    @Override
    public NssData decodeSnapshot(String code, RawData rawData) {
        final List<String> value = (List<String>) rawData.getData();
        NssData nssData = new NssData(null);
        List<Map> obj = new ArrayList<Map>();
        String securityMarket = SecurityCodeHelper.getSecurityMarketType(code);
        int tradingDay =
                AsaDataHelper.getMarketTradingDay(securityMarket, getContext());
        if (tradingDay == 0) {
            log.info("invalid trading day");
        } else if (value.size() > 0) {
            for (String row : value) {
                // "1800,92000,1,U, ,98000,8.570"
                // code, time, transNo, transType, bidaskflag, vol, price
                // 0     1     2        3          4           5    6
                if (row.length() == 0) {
                    continue;
                }
                List<String> val = Arrays.asList(row.split(","));
                int timeString = Integer.parseInt(val.get(1), 10);
                int hour = (int) Math.floor(timeString / 10000);
                int min = (int) Math.floor((timeString - hour * 10000) / 100);
                int sec = (int) Math.floor((timeString - hour * 10000) - min * 100);
                int timeInfoToMillSec = (hour * 3600 + min * 60 + sec) * 1000;
                int time = tradingDay + timeInfoToMillSec;
                // [3] is not being used.
                obj.add(new HashMap<Object, Object>() {{
                    put("nominal", Double.parseDouble(val.get(6)));
                    put("open", Double.parseDouble(val.get(6)));
                    put("high", Double.parseDouble(val.get(6)));
                    put("low", Double.parseDouble(val.get(6)));
                    put("close", Double.parseDouble(val.get(6)));
                    put("volume", Double.parseDouble(val.get(5)));
                    put("tranType", val.get(3));
                    put("dates", val.get(1));
                    put("transNo", Integer.parseInt(val.get(2), 10));
                    put("timestamp", time);
                    put("tradingDay", tradingDay);
                    put("tradingAdd", timeInfoToMillSec);
                    put("bidAskFlag", val.get(4));
                    put("hist", true);
                }});
            }
        }
        nssData.setData(obj);
        nssData.setSnapshot(true);
        nssData.setReady(true);
        return nssData;
    }

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = rawData.getData().toString();
        NssData nssData = new NssData(null);

        // 5.42|9000| |A|10:05|1006#
        // Cache
        List<Map> obj = new ArrayList<Map>();
        String securityMarket = SecurityCodeHelper.getSecurityMarketType(code);
        int tradingDay =
                AsaDataHelper.getMarketTradingDay(securityMarket, getContext());
        if (tradingDay == 0) {
            log.info("invalid trading day");
        } else if (value.trim().length() > 0) {
            List<String> original = Arrays.asList(value.split("#"));
            for (String originValue : original) {
                List<String> val = Arrays.asList(originValue.split("|"));
                List<String> timeInfo = Arrays.asList(val.get(4).split(":"));
                int timeInfoToMillSec = (Integer.parseInt(timeInfo.get(0), 10) *3600 +
                        Integer.parseInt(timeInfo.get(1), 10) *60 +
                        Integer.parseInt(timeInfo.get(2), 10)) *
                1000;
                int time = tradingDay + timeInfoToMillSec;
                // [3] is not being used.
                obj.add(new HashMap<Object, Object>() {{
                    put("nominal", Double.parseDouble(val.get(0)));
                    put("open", Double.parseDouble(val.get(0)));
                    put("high", Double.parseDouble(val.get(0)));
                    put("low", Double.parseDouble(val.get(0)));
                    put("close", Double.parseDouble(val.get(0)));
                    put("volume", Double.parseDouble(val.get(1)));
                    put("tranType", val.get(2));
                    put("dates", val.get(4));
                    put("transNo", Integer.parseInt(val.get(5), 10));
                    put("timestamp", time);
                    put("tradingDay", tradingDay);
                    put("tradingAdd", timeInfoToMillSec);
                    put("hist", true);
                }});
            }
        }

        nssData.setData(obj);
        nssData.setReady(true);
        return nssData;
    }
}
