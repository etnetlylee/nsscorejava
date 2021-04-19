package decoder;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.RawData;
import coreStorage.model.AsaStorage;
import model.SearchListItem;

public class DecoderSearchList extends Decoder {
    static final String uniqueID = "search-list";
    final Logger log = Logger.getLogger("DecoderDisclaimers");

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        AsaStorage asa = getContext().getAsaStorage();
        ObjectMapper mapper = new ObjectMapper();
        final String value = (rawData.getData()).toString();
        try {
            Map<String, List<SearchListItem>> obj = mapper.readValue(value, Map.class);

            for (String type : obj.keySet()){
                List<SearchListItem> _tempList = obj.get(type);
                asa.SEARCH_LIST.put(type,_tempList);
            }
        } catch (Exception e) {
            log.warning(e.getMessage());
        }
        return null;
    }
}
