package com.etnet.coresdk.decoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.etnet.coresdk.coreModel.Decoder;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.RawData;
import com.etnet.coresdk.coreStorage.model.AsaStorage;

import static com.etnet.coresdk.constants.AsaConstant.LANG_EN;
import static com.etnet.coresdk.constants.AsaConstant.LANG_SC;
import static com.etnet.coresdk.constants.AsaConstant.LANG_TC;

public class Decoder29 extends Decoder {
    public static final String uniqueID = "29";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = (rawData.getData()).toString();
        AsaStorage data = getContext().getAsaStorage();
        if (value != null && value.trim() != "") {
            final List<String> values = Arrays.asList(value.split("&&"));
            List<String> engList = new ArrayList<String>();
            List<String> scList = new ArrayList<String>();
            List<String> tcList = new ArrayList<String>();

            for (String val : values) {
                final List<String> names = Arrays.asList(val.split(","));
                if (names != null && names.size() == 3) {
                    final String engName = names.get(0);
                    final String scName = names.get(1);
                    final String tcName = names.get(2);

                    engList.add(engName);
                    scList.add(scName);
                    tcList.add(tcName);
                }
            }
            data.ISSUMAP.put(LANG_EN, engList);
            data.ISSUMAP.put(LANG_SC, scList);
            data.ISSUMAP.put(LANG_TC, tcList);
        }
        return null;
    }
}
