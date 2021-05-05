package com.etnet.coresdk.decoder;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.etnet.coresdk.coreModel.Decoder;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.RawData;
import com.etnet.coresdk.coreStorage.model.AsaStorage;
import com.etnet.coresdk.model.Disclaimer;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DecoderDisclaimers extends Decoder {
    public static final String uniqueID = "disclaimers";

    final Logger log = Logger.getLogger("DecoderDisclaimers");

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        AsaStorage asa = getContext().getAsaStorage();
        ObjectMapper mapper = new ObjectMapper();
        final String value = (rawData.getData()).toString();
        try {
            Map<String, List<Disclaimer>> obj = mapper.readValue(value, Map.class);

            for (String name : obj.keySet()){
                if (obj.containsKey(name)) {
                    List<Disclaimer> _tempList = obj.get(name);
                    asa.DISCLAIMERS.put(name,_tempList);
                }
            }
        } catch (Exception e) {
            log.warning(e.getMessage());
        }

        return null;
    }
}
