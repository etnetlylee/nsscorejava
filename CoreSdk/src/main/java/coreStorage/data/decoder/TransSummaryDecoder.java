package coreStorage.data.decoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.RawData;
import model.TransItem;

public class TransSummaryDecoder extends Decoder {
    public static final String uniqueID = "transsummary";

    @Override
    public NssData decodeSnapshot(String code, RawData rawData) {
        final List<String> value = (List<String>) rawData.getData();
        List<TransItem> transSummary = new ArrayList<TransItem>();
        List<Map<String, Double>> transSummaryItems = new ArrayList<Map<String, Double>>();

        for (String row : value) {
            List<String> data = Arrays.asList(row.split(","));
            if (data.size() >= 6) {
                Map<String, Double> item = new HashMap<String, Double>() {{
                    put("timestamp", Double.parseDouble(data.get(1)));
                    put("price", Double.parseDouble(data.get(2)));
                    put("buyvolume", Double.parseDouble(data.get(3)));
                    put("sellvolume", Double.parseDouble(data.get(4)));
                    put("othervolume", Double.parseDouble(data.get(5)));
                }};
                transSummaryItems.add(item);
            } else if (data.size() >= 5) {
                Map<String, Double> amsItem = new HashMap<String, Double>() {{
                    put("timestamp", Double.parseDouble(data.get(1)));
                    put("price", Double.parseDouble(data.get(2)));
                    put("amsvolume", Double.parseDouble(data.get(3)));
                    put("nonamsvolume", Double.parseDouble(data.get(4)));
                }};
                transSummaryItems.add(amsItem);
            } else if (data.size() > 2) {
                final double price = Double.parseDouble(data.get(1));
                final double volumeAMS = Double.parseDouble(data.get(2));
                double volumeNonAMS = 0;
                if (data.size() > 4) {
                    volumeNonAMS = Double.parseDouble(data.get(4));
                }
                TransItem item = new TransItem(price, volumeAMS, volumeNonAMS);
                transSummary.add(item);
            }
        }
        NssData nssData = new NssData(null);
        if (transSummaryItems.size() > 0) {
            nssData.setData(transSummaryItems);
        } else {
            nssData.setData(transSummary);
        }
        nssData.setReady(true);

        return nssData;
    }
}
