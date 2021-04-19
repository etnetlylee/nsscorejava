package decoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.RawData;
import coreStorage.model.AsaStorage;

public class Decoder89US extends Decoder {
    static final String uniqueID = "89US";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = (rawData.getData()).toString();
        AsaStorage data = getContext().getAsaStorage();
        // BugFixed, Problem occurred in viutv, need clear this list before add.
        data.US_INDEX_LIST = new ArrayList<Object>();

        if (value != null && value.trim() != "") {
            final List<String> values = Arrays.asList(value.split("|"));
            for (int i = 0; i < values.size(); i++) {
                final List<String> temp = Arrays.asList(values.get(i).split(","));
                if (temp != null && temp.size() > 1) {
                    Map<String, String> _object  = new HashMap<String, String>() {{
                        put("code", temp.get(0));
                        put("name", temp.get(1));
                    }};
                    data.US_INDEX_LIST.add(_object);
                }
            }
        }
        return null;
    }
}
