package com.etnet.coresdk.decoder;

import com.etnet.coresdk.coreModel.Decoder;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.RawData;

public class Decoder31 extends Decoder {
    public static final String uniqueID = "31";

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
