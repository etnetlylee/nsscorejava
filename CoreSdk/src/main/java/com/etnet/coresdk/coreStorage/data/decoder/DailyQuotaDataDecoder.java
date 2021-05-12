package com.etnet.coresdk.coreStorage.data.decoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.etnet.coresdk.coreModel.Decoder;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.RawData;
import com.etnet.coresdk.coreStorage.model.DailyQuotaData;

public class DailyQuotaDataDecoder extends Decoder {
    public static final String uniqueID = "dailyquotadata";

    @Override
    public NssData decodeSnapshot(String code, RawData rawData) {
        final List<String> value = (List<String>) rawData.getData();
        List<DailyQuotaData> data = new ArrayList<DailyQuotaData>();
        for (String rows : value) {
            final List<String> row = Arrays.asList(rows.split(","));
            if (row != null && row.size() >= 12) {
                DailyQuotaData dailyData = new DailyQuotaData(
                        Integer.parseInt(row.get(0), 10),
                        Integer.parseInt(row.get(0), 10),
                        Double.parseDouble(row.get(1)),
                        Double.parseDouble(row.get(2)),
                        Double.parseDouble(row.get(3)),
                        Double.parseDouble(row.get(4)),
                        Double.parseDouble(row.get(5)),
                        Double.parseDouble(row.get(6)),
                        Double.parseDouble(row.get(12)),
                        Double.parseDouble(row.get(1)) + Double.parseDouble(row.get(8)),
                        Double.parseDouble(row.get(3)) -
                                Double.parseDouble(row.get(5)) +
                                Double.parseDouble(row.get(9)) -
                                Double.parseDouble(row.get(10)),
                        Double.parseDouble(row.get(7)) + Double.parseDouble(row.get(11)),
                        Double.parseDouble(row.get(12)) +
                                Double.parseDouble((row.get(13) != null) ? row.get(13) : "0")

                );
                data.add(dailyData);
            }
        }

        NssData nssData = new NssData(null);
        nssData.setData(nssData);
        nssData.setReady(true);
        return nssData;
    }
}
