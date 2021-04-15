package decoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.RawData;
import coreStorage.model.AsaStorage;

public class Decoder7 extends Decoder {
    static final String uniqueID = "7";
    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = (rawData.getData()).toString();
        AsaStorage data = getContext().getAsaStorage();
        if (value != null && value.trim() != "") {
            final List<String> values = Arrays.asList(value.split("\\$"));
            for(String val : values){
                final String spreadType = val.split(":")[0];
                if (!data.SPREAD_KEY_MAP.containsKey(spreadType)) {
                    data.SPREAD_KEY_MAP.put(spreadType, new ArrayList<String>());
                }
                if (!data.SPREAD_MAP.containsKey(spreadType)) {
                    data.SPREAD_MAP.put(spreadType, new HashMap<String, String>());
                }
                final String tmp = val.split(":")[1];
                final List<String> arrays = Arrays.asList(tmp.split("|"));
                for(String arr : arrays){
                    final List<String> tables = Arrays.asList(arr.split("&"));
                    for (String table : tables){
                        String content = "";
                        if (table.contains(",")) {
                            content = table.split(",")[2];
                        } else {
                            content = table;
                        }
                        final String key = content.split(" ")[0];
                        final String price = content.split(" ")[1];

                        data.SPREAD_KEY_MAP.get(spreadType).add(key);
                        data.SPREAD_MAP.get(spreadType).put(key, price);
                    }
                }
            }
        }

        return null;
    }
}
