package coreStorage.data.decoder;

import java.util.Arrays;
import java.util.List;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.RawData;
import coreStorage.model.SpeFlow;
import coreStorage.model.SpeFlowQueue;

public class SpeFlowDecoder extends Decoder {
    public static final String uniqueID = "speflow";

    @Override
    public NssData decodeSnapshot(String code, RawData rawData) {
        final List<String> value = (List<String>) rawData.getData();
        SpeFlowQueue speFlowQueueStruct = new SpeFlowQueue();
        for (String val : value){
            final List<String> values = Arrays.asList(val.split(","));
            if (Double.parseDouble(values.get(1)) > 0) {
                SpeFlow speFlowStruct = new SpeFlow();
                speFlowStruct.setCode(values.get(0));
                speFlowStruct.setFlow(Double.parseDouble(values.get(1)));
                speFlowStruct.setTurnover(Double.parseDouble(values.get(2)));
                speFlowStruct.setVolume(Integer.parseInt(values.get(3), 10));
                speFlowStruct.setAverage(Double.parseDouble(values.get(4)));
                speFlowStruct.setVolumeRate(Double.parseDouble(values.get(5)));
                speFlowStruct.setTurnoverRate(Double.parseDouble(values.get(6)));

                speFlowQueueStruct.add(speFlowStruct);
            }
        }

        NssData nssData = new NssData(null);
        nssData.setData(speFlowQueueStruct);
        nssData.setReady(true);
        return nssData;
    }
}
