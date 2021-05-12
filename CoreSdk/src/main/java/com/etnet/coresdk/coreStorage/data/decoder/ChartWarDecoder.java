package com.etnet.coresdk.coreStorage.data.decoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.etnet.coresdk.coreModel.Decoder;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.RawData;
import com.etnet.coresdk.coreStorage.model.WarrantChartRecord;

public class ChartWarDecoder extends Decoder {
    public static final String uniqueID = "chartwar";

    @Override
    public NssData decodeSnapshot(String code, RawData rawData) {
        final List<String> value = (List<String>) rawData.getData();
        List<WarrantChartRecord> data = new ArrayList<WarrantChartRecord>();
        // 16074,1496246400000,0.217,320000,0.215,320000,0,0.000,640000,200000000,34.653,0.000,0.000,0.000,21.516,0.202,
        for (String rows : value) {
            final List<String> row = Arrays.asList(rows.split(","));
            if (row != null && row.size() >= 16) {
                WarrantChartRecord chartRecord = new WarrantChartRecord(
                        Integer.parseInt(row.get(1), 10),
                        Double.parseDouble(row.get(2)),
                        Double.parseDouble(row.get(3)),
                        Double.parseDouble(row.get(4)),
                        Double.parseDouble(row.get(5)),
                        Double.parseDouble(row.get(6)),
                        Double.parseDouble(row.get(7)),
                        Double.parseDouble(row.get(8)),
                        Double.parseDouble(row.get(9)),
                        Double.parseDouble(row.get(10)),
                        Double.parseDouble(row.get(11)),
                        Double.parseDouble(row.get(12)),
                        Double.parseDouble(row.get(13)),
                        Double.parseDouble(row.get(14)),
                        Double.parseDouble(row.get(15))
                );
                data.add(chartRecord);
            }
        }

        Collections.reverse(data);
        List<WarrantChartRecord> unshiftData = new ArrayList<WarrantChartRecord>(data);

        NssData nssData = new NssData(null);
        nssData.setData(unshiftData);
        nssData.setSnapshot(true);
        nssData.setReady(true);
        return nssData;
    }
}
