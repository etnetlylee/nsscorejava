package com.etnet.coresdk.coreStorage.data.decoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.etnet.coresdk.coreModel.Decoder;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.RawData;

public class Top10StockConnectTurnoverDecoder extends Decoder {
    public static final String uniqueID = "top10stockconnectturnover";

    @Override
    public NssData decodeSnapshot(String code, RawData rawData) {
        final List<String> value = (List<String>) rawData.getData();
        List<Map<String, List>> data = new ArrayList<Map<String, List>>();
        int previousTimeStamp = 0;
        Map<String, List> dayObject = new HashMap<String, List>();
        for (String rows : value) {
            List<String> row = Arrays.asList(rows.split(","));
            if (row != null && row.size() >= 7) {
                if (previousTimeStamp != Integer.parseInt(row.get(0), 10)) {
                    data.add(dayObject);
                    dayObject = new HashMap<String, List>();
                }

                if (!dayObject.containsKey(row.get(3))) {
                    dayObject.put(row.get(3), new ArrayList());
                }

                Map<String, Object> rowData = new HashMap<String, Object>() {{
                    put("date", Integer.parseInt(row.get(0), 10));
                    put("timestamp", Integer.parseInt(row.get(0), 10));
                    put("code", row.get(1));
                    put("direction", row.get(2));
                    put("exchange", row.get(3));
                    put("buyTurnover", Double.parseDouble(row.get(4)));
                    put("askTurnover", Double.parseDouble(row.get(5)));
                    put("netBuyAskTurnover", Double.parseDouble(row.get(4)) + Double.parseDouble(row.get(5)));
                    put("totalTurnover", Double.parseDouble(row.get(6)));
                    put("perTotalTurnover", ((Double.parseDouble(row.get(4)) + Double.parseDouble(row.get(5))) /
                            Double.parseDouble(row.get(6)) *
                            100));
                }};

                dayObject.get(row.get(3)).add(rowData);
                previousTimeStamp = Integer.parseInt(row.get(0), 10);
            }
        }

        data.add(dayObject);

        NssData nssData = new NssData(null);
        nssData.setData(data);
        nssData.setReady(true);
        return nssData;
    }
}
