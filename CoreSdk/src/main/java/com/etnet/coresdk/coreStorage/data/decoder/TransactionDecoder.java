package com.etnet.coresdk.coreStorage.data.decoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.etnet.coresdk.coreModel.Decoder;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.QuoteData;
import com.etnet.coresdk.coreModel.RawData;
import com.etnet.coresdk.util.AsaDataHelper;
import com.etnet.coresdk.util.SecurityCodeHelper;

public class TransactionDecoder extends Decoder {
    public static final String uniqueID = "transaction";

    public int parseTime(int tradingDay, String time) {
        Calendar t = Calendar.getInstance();
        t.setTimeInMillis(tradingDay);

        final List<String> v = Arrays.asList(time.split(":"));
        int mYear = t.get(Calendar.YEAR);
        int mMonth = t.get(Calendar.MONTH);
        int mDay = t.get(Calendar.DAY_OF_MONTH);

        int hour = Integer.parseInt(v.get(0), 10);
        int mins = Integer.parseInt(v.get(1), 10);
        int seconds = (v.size() > 2) ? Integer.parseInt(v.get(2), 10) : 0;
        t.set(Calendar.YEAR, mYear);
        t.set(Calendar.MONTH, mMonth);
        t.set(Calendar.DAY_OF_MONTH, mDay);
        t.set(Calendar.HOUR, hour);
        t.set(Calendar.MINUTE, mins);
        t.set(Calendar.SECOND, seconds);

        return (int) t.getTimeInMillis();
    }

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
        List<Map<String, Object>> dataArray = new ArrayList<Map<String, Object>>();
        if (nssData.getData() != null) {
            dataArray = (List<Map<String, Object>>) nssData.getData();
        }
        final List<String> trans = Arrays.asList(value.split("#"));
        final String codeType = SecurityCodeHelper.getSecurityMarketType(code);
        final int tradingDay =
                AsaDataHelper.getMarketTradingDay(codeType, getContext());
        for (String tran : trans) {
            if (tran.trim().length() > 0) {
                final List<String> val = Arrays.asList(tran.split("|"));
                Map<String, Object> data = new HashMap<String, Object>() {{
                    put("price", Double.parseDouble(val.get(0)));
                    put("volume", Double.parseDouble(val.get(1)));
                    put("transType", val.get(2));
                    put("bidAskFlag", val.get(3));
                    put("time", parseTime(tradingDay, val.get(4)));
                    put("transNo", Integer.parseInt(val.get(5), 10));
                    put("type", "TransactionDecoder");
                    put("tradingDay", tradingDay);
                }};
                if (dataArray.size() > 0) {
                    if ((int) data.get("transNo") > (int) dataArray.get(dataArray.size() - 1).get("transNo")) {
                        dataArray.add(data);
                    }
                } else {
                    dataArray.add(data);
                }
            }

        }

        // maintain cached 20 records
        if (dataArray.size() > 20) {
            dataArray.subList(0, dataArray.size() - 20).clear();
        }
        nssData.setData(dataArray);
        nssData.setReady(true);
        return nssData;
    }
}
