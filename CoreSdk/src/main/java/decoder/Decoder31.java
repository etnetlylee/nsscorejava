package decoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.RawData;
import coreStorage.model.AsaStorage;

import static constants.AsaConstant.LANG_EN;
import static constants.AsaConstant.LANG_SC;
import static constants.AsaConstant.LANG_TC;

public class Decoder31 extends Decoder {
    static final String uniqueID = "31";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        // final String value = (rawData.getData()).toString();
        // AsaStorage data = getContext().getAsaStorage();
        // if (value != null && value.trim() != '') {
        //   if (value != null && value.length != 0) {
        //     for (int i = 0; i < value.length; i++) {
        //       final List<String> links = value[i].split(',');
        //       final String urlKey = links[0];
        //       IEInfoStruct struct = data.URL_INFO_MAP[urlKey];
        //       if (struct == null) {
        //         struct = IEInfoStruct(links);
        //         data.URL_INFO_MAP[urlKey] = struct;
        //       }
        //     }
        //   }
        // }
        return null;
    }
}
