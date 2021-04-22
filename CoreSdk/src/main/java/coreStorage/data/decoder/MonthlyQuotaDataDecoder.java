package coreStorage.data.decoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.AEADBadTagException;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.RawData;
import coreStorage.model.MonthlyQuotaData;

public class MonthlyQuotaDataDecoder extends Decoder {
    public static final String uniqueID = "monthlyquotadata";

    @Override
    public NssData decodeSnapshot(String code, RawData rawData) {
        final List<String> value = (List<String>) rawData.getData();

        List<MonthlyQuotaData> data = new ArrayList<MonthlyQuotaData>();
        for (String rows : value) {
            final List<String> row = Arrays.asList(rows.split(","));

            if (row.size() >= 12) {
                MonthlyQuotaData monthlyData = new MonthlyQuotaData(
                        Integer.parseInt(row.get(0), 10),
                        Integer.parseInt(row.get(0), 10),
                        Double.parseDouble(row.get(1)),
                        Double.parseDouble(row.get(2)),
                        Double.parseDouble(row.get(3)),
                        Double.parseDouble(row.get(4)),
                        Double.parseDouble(row.get(5)),
                        Double.parseDouble(row.get(5)),
                        Double.parseDouble(row.get(7)),
                        Double.parseDouble(row.get(8)),
                        Double.parseDouble(row.get(10)),
                        Double.parseDouble(row.get(11)),
                        Double.parseDouble(row.get(12)),
                        Double.parseDouble(row.get(1)) -
                                Double.parseDouble(row.get(5)) +
                                Double.parseDouble(row.get(13)) -
                                Double.parseDouble(row.get(14)),
                        Double.parseDouble(row.get(9)) + Double.parseDouble(row.get(15)),
                        Double.parseDouble(row.get(16)) +
                                Double.parseDouble((row.get(17) != null) ? row.get(17) : "0")
                );
                data.add(monthlyData);
            }
        }


        NssData nssData = new NssData(null);
        nssData.setReady(true);
        nssData.setData(data);
        return nssData;
    }
}
