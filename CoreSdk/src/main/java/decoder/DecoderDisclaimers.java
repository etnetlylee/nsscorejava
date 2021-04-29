package decoder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.RawData;
import coreStorage.model.AsaStorage;
import model.Disclaimer;
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
