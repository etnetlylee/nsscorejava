package com.etnet.coresdk.coreStorage.data.decoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.etnet.coresdk.coreModel.Decoder;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.RawData;

public class ShortSellDecoder extends Decoder {
    public static final String uniqueID = "shortsell";

    @Override
    public NssData decodeSnapshot(String code, RawData rawData) {
        String value = (rawData.getData()).toString();
        List<Map<String, Double>> data = new ArrayList<Map<String, Double>>();
        for (int i = 0; i < value.length(); i++) {
            List<String> row = Arrays.asList(Character.toString(value.charAt(i)).split(","));
            if (row.size() >= 7) {
                Map<String, Double> daybar = new HashMap<String, Double>() {{
                    put("timestamp", Double.parseDouble(row.get(1)));
                    put("boardsumtrn", Double.parseDouble(row.get(2)));
                    put("boardsumtrn5sma", Double.parseDouble(row.get(3)));
                    put("stockturnover", Double.parseDouble(row.get(4)));
                    put("perssturnover", Double.parseDouble(row.get(5)));
                    put("totalturnover", Double.parseDouble(row.get(6)));
        }};
                data.add(daybar);
            }
        }
        Collections.reverse(data);
        List<Map<String, Double>> unshiftData = new ArrayList<Map<String, Double>> (data);

        NssData nssData = new NssData(null);
        nssData.setReady(true);
        nssData.setData(unshiftData);
        return nssData;
    }
}
