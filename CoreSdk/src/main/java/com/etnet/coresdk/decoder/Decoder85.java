package com.etnet.coresdk.decoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.etnet.coresdk.coreModel.Decoder;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.RawData;
import com.etnet.coresdk.coreStorage.model.AsaStorage;

import static com.etnet.coresdk.constants.AsaConstant.LANG_SC;
import static com.etnet.coresdk.constants.AsaConstant.LANG_TC;

public class Decoder85 extends Decoder {
    public static final String uniqueID = "85";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = (rawData.getData()).toString();
        AsaStorage data = getContext().getAsaStorage();
        if (value != null && value.trim() != "") {
            final List<String> values = Arrays.asList(value.split("|"));

            for (String columnlist : values) {
                final List<String> infos = Arrays.asList(columnlist.split(","));
                final String type = infos.get(0);
                final String nameTc = infos.get(1);
                final String nameSc = infos.get(2);

                if (type == "c") {
                    // commentator
                    if (data.HSHARE_COMMENTATOR_LIST.get(LANG_TC) != null) {
                        data.HSHARE_COMMENTATOR_LIST.put(LANG_TC, data.HSHARE_COMMENTATOR_LIST.get(LANG_TC));
                    } else {
                        data.HSHARE_COMMENTATOR_LIST.put(LANG_TC, new ArrayList<String>());
                    }

                    if (!data.HSHARE_COMMENTATOR_LIST.get(LANG_TC).contains(nameTc)) {
                        data.HSHARE_COMMENTATOR_LIST.get(LANG_TC).add(nameTc);
                    }

                    if (data.HSHARE_COMMENTATOR_LIST.get(LANG_SC) != null) {
                        data.HSHARE_COMMENTATOR_LIST.put(LANG_SC, data.HSHARE_COMMENTATOR_LIST.get(LANG_SC));
                    } else {
                        data.HSHARE_COMMENTATOR_LIST.put(LANG_SC, new ArrayList<String>());
                    }

                    if (!data.HSHARE_COMMENTATOR_LIST.get(LANG_SC).contains(nameSc)) {
                        data.HSHARE_COMMENTATOR_LIST.get(LANG_SC).add(nameSc);
                    }
                } else if (type == "re") {
                    // recommentator
                    if (data.HSHARE_RECOMMENTATOR_LIST.get(LANG_SC) != null) {
                        data.HSHARE_RECOMMENTATOR_LIST.put(LANG_SC, data.HSHARE_RECOMMENTATOR_LIST.get(LANG_SC));
                    } else {
                        data.HSHARE_RECOMMENTATOR_LIST.put(LANG_SC, new ArrayList<String>());
                    }

                    if (!data.HSHARE_RECOMMENTATOR_LIST.get(LANG_SC).contains(nameTc)) {
                        data.HSHARE_RECOMMENTATOR_LIST.get(LANG_SC).add(nameTc);
                    }

                    if (data.HSHARE_RECOMMENTATOR_LIST.get(LANG_TC) != null) {
                        data.HSHARE_RECOMMENTATOR_LIST.put(LANG_TC, data.HSHARE_RECOMMENTATOR_LIST.get(LANG_TC));
                    } else {
                        data.HSHARE_RECOMMENTATOR_LIST.put(LANG_TC, new ArrayList<String>());
                    }

                    if (!data.HSHARE_RECOMMENTATOR_LIST.get(LANG_TC).contains(nameSc)) {
                        data.HSHARE_RECOMMENTATOR_LIST.get(LANG_TC).add(nameSc);
                    }
                }
            }
        }
        return null;
    }
}
