package coreStorage.data.decoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.RawData;
import coreStorage.model.Transaction;

public class MoneyFlowDecoder extends Decoder {
    public static final String uniqueID = "moneyflow";

    @Override
    public NssData decodeSnapshot(String code, RawData rawData) {
        final List<String> value = (List<String>) rawData.getData();
        List<Map<String, Double>> data = new ArrayList<Map<String, Double>>();
        for (String rows : value){
            final List<String> row = Arrays.asList(rows.split(","));
            if (row.size() >= 12) {
                Map<String, Double> daybar = new HashMap<String, Double>() {{
                    put("timestamp", Double.parseDouble(row.get(0)));
                    put("amturnoverbuy", Double.parseDouble(row.get(1)));
                    put("amturnoversell", Double.parseDouble(row.get(2)));
                    put("pmturnoverbuy", Double.parseDouble(row.get(3)));
                    put("pmturnoversell", Double.parseDouble(row.get(4)));
                    put("moneyflow", Double.parseDouble(row.get(5)));
                    put("amturnoverblkbuy", Double.parseDouble(row.get(6)));
                    put("amturnoverblksell", Double.parseDouble(row.get(7)));
                    put("pmturnoverblkbuy", Double.parseDouble(row.get(8)));
                    put("pmturnoverblksell", Double.parseDouble(row.get(9)));
                    put("moneyflowblk", Double.parseDouble(row.get(10)));
                    put("turnover", Double.parseDouble(row.get(11)));
        }};
                data.add(daybar);
            }
        }
        Collections.reverse(data);
        List<Map<String, Double>> unshiftData = new ArrayList<Map<String, Double>> (data);

        NssData nssData = new NssData(null);
        nssData.setData(unshiftData);
        nssData.setReady(true);
        return nssData;
    }
}
