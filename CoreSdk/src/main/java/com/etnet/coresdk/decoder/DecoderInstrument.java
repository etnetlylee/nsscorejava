package com.etnet.coresdk.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.etnet.coresdk.coreModel.Decoder;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.RawData;
import com.etnet.coresdk.coreStorage.model.AsaStorage;
import com.etnet.coresdk.model.InstrumentInfo;

public class DecoderInstrument extends Decoder {
    public static final String uniqueID = "instrument";

    final Logger log = Logger.getLogger("DecoderInstrument");

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        AsaStorage asa = getContext().getAsaStorage();
        ObjectMapper mapper = new ObjectMapper();
        final String value = (rawData.getData()).toString();
        try {
            Map<String, List<InstrumentInfo>> obj = mapper.readValue(value, Map.class);

            for (String type : obj.keySet()) {
                if (obj.containsKey(type)) {
                    List<InstrumentInfo> _tempList = obj.get(type);
                    asa.INSTRUMENT_NAME.put(type,_tempList);
                }
            }
        } catch (Exception e) {
            log.warning(e.getMessage());
        }

        return null;
    }
}
