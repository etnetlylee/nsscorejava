package coreStorage.data.decoder;

import java.util.Arrays;
import java.util.List;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.RawData;
import coreStorage.model.SpeStock;
import coreStorage.model.SpeStockQueue;

public class SpeStkDecoder extends Decoder {
    public static final String uniqueID = "spestk";

    @Override
    public NssData decodeSnapshot(String code, RawData rawData) {
        final List<String> value = (List<String>) rawData.getData();

        SpeStockQueue speStkQueueStruct = new SpeStockQueue();
        for (String speString : value) {
            if (speString.trim().length() > 0) {
                SpeStock speStkStruct = new SpeStock();
                final List<String> values = Arrays.asList(speString.split(","));
                speStkStruct.setBrokerCode(values.get(0));
                speStkStruct.setTurnover(Double.parseDouble(values.get(1)));
                speStkStruct.setVolume(Integer.parseInt(values.get(2), 10));
                speStkStruct.setSellVolume(Integer.parseInt(values.get(3), 10));
                speStkStruct.setBuyVolume(Integer.parseInt(values.get(4), 10));
                speStkStruct.setSellTurnover(Double.parseDouble(values.get(5)));
                speStkStruct.setBuyTurnover(Double.parseDouble(values.get(6)));
                speStkStruct.setDifferVolume(Integer.parseInt(values.get(7), 10));
                speStkStruct.setDifferTurnover(Double.parseDouble(values.get(8)));
                speStkStruct.setAverage(Double.parseDouble(values.get(9)));

                speStkQueueStruct.add(speStkStruct);
            }
        }

        NssData nssData = new NssData(null);
        nssData.setData(speStkQueueStruct);
        nssData.setReady(true);
        return nssData;
    }
}
