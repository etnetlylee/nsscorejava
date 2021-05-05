package com.etnet.coresdk.coreStorage.data.decoder;

import java.util.Arrays;
import java.util.List;

import com.etnet.coresdk.coreModel.Decoder;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.RawData;
import com.etnet.coresdk.coreStorage.model.SpeBroker;
import com.etnet.coresdk.coreStorage.model.SpeBrokerQueue;

public class SpeBroDecoder extends Decoder {
    public static final String uniqueID = "spebro";

    @Override
    public NssData decodeSnapshot(String code, RawData rawData) {
        final List<String> value = (List<String>) rawData.getData();
        SpeBrokerQueue speBroQueueStruct = new SpeBrokerQueue();
        for (String speString : value){
            SpeBroker speBroStruct = new SpeBroker();
            final List<String> values = Arrays.asList(speString.split(","));
            if (values.size() >= 12) {
                speBroStruct.setStockCode(values.get(0));
                speBroStruct.setTurnover(Double.parseDouble(values.get(1)));
                speBroStruct.setVolume(Integer.parseInt(values.get(2), 10));
                speBroStruct.setSellVolume(Integer.parseInt(values.get(3),  10));
                speBroStruct.setBuyVolume(Integer.parseInt(values.get(4), 10));
                speBroStruct.setSellTurnover(Double.parseDouble(values.get(5)));
                speBroStruct.setBuyTurnover(Double.parseDouble(values.get(6)));
                speBroStruct.setDifferVolume(Integer.parseInt(values.get(7), 10));
                speBroStruct.setDifferTurnover(Double.parseDouble(values.get(8)));
                speBroStruct.setAverage(Double.parseDouble(values.get(9)));

                speBroStruct.setType(values.get(11)); // Added by Rocky

                speBroQueueStruct.add(speBroStruct);
            }
        }

        NssData nssData = new NssData(null);
        nssData.setData(speBroQueueStruct);
        nssData.setReady(true);
        return nssData;
    }
}
