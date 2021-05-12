package com.etnet.coresdk.coreStorage.data.decoder;

import java.util.Arrays;
import java.util.List;

import com.etnet.coresdk.coreModel.Decoder;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.RawData;
import com.etnet.coresdk.coreStorage.model.Region;

public class RegionDecoder extends Decoder {
    public static final String uniqueID = "region";

    @Override
    public NssData decodeSnapshot(String code, RawData rawData) {
        final List<String> value = (List<String>) rawData.getData();
        Region region = new Region();
        NssData nssData = new NssData(null);
        if (value != null && value.size() > 0) {
            for (String row : value) {
                final List<String> items = Arrays.asList(row.split(","));
                for (String item : items){
                    final double val = Double.parseDouble(item);
                    if (val != Double.NaN) {
                        region.add(val);
                    }
                }
            }

            nssData.setReady(true);
        }
        nssData.setData(region);
        return nssData;
    }
}
