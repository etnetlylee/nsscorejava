package com.etnet.coresdk.decoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.etnet.coresdk.coreModel.Decoder;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.RawData;
import com.etnet.coresdk.coreStorage.model.AsaStorage;

public class Decoder90US extends Decoder {
    public static final String uniqueID = "90US";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = (rawData.getData()).toString();
        AsaStorage data = getContext().getAsaStorage();
        // BugFixed, Problem occurred in viutv, need clear this list before add.
        data.US_INDUSTRY_CODE_LIST = new ArrayList<Object>();

        if (value != null && value.trim() != "") {
            final List<String> values = Arrays.asList(value.split("|"));
            for (int i = 0; i < values.size(); i++) {
                final List<String> temp = Arrays.asList(values.get(i).split(","));
                if (temp != null && temp.size() > 1) {
                    Map<String, String> _object  = new HashMap<String, String>() {{
                        put("code", temp.get(0));
                        put("name", temp.get(1));
                    }};
                    data.US_INDUSTRY_CODE_LIST.add(_object);
                }
            }
        }
        return null;
    }
}
