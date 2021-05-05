package com.etnet.coresdk.decoder;

import java.util.Arrays;
import java.util.List;

import com.etnet.coresdk.coreModel.Decoder;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.RawData;
import com.etnet.coresdk.coreStorage.model.AsaStorage;
import com.etnet.coresdk.model.StockInfo;
import com.etnet.coresdk.util.DataHelper;

import static com.etnet.coresdk.constants.SecurityType.SECURITYTYPE_STOCK;

public class Decoder70 extends Decoder {
    public static final String uniqueID = "70";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = (rawData.getData()).toString();
        AsaStorage data = getContext().getAsaStorage();
        if (value != null && value.trim() != "") {
            final List<String> values = Arrays.asList(value.split("|"));

            for (String codeInfo : values) {
                final List<String> info = Arrays.asList(codeInfo.split(","));
                if (info.size() <= 0) {
                    continue;
                }
                final String SZCode = info.get(0);
                if (!SZCode.contains(".")) {
                    continue;
                }
                final String trimmedSZCode = SZCode.split(".")[1];
                final String target = info.get(1);
                if (DataHelper.binarySearch(data.SZ_SHARE_LIST, trimmedSZCode) == -1) {
                    DataHelper.insertSorted(data.SZ_SHARE_LIST, trimmedSZCode);
                }
                if (DataHelper.binarySearch(data.SZ_SHARE_LIST, SZCode) == -1) {
                    DataHelper.insertSorted(data.SZ_SHARE_LIST, SZCode);
                }
                if (DataHelper.binarySearch(data.VALIDCODE, trimmedSZCode) == -1) {
                    DataHelper.insertSorted(data.VALIDCODE, trimmedSZCode);
                }

                data.STKTYPEMAP.put(SZCode, SECURITYTYPE_STOCK);
                if (target != "CHA" && target != "A" && target != "SMA") {
                    // Should be error
                }
                final String traName = info.get(2) != null ? info.get(2) : "";
                final String simName = info.get(3) != null ? info.get(3) : "";
                final String engName = info.get(4) != null ? info.get(4) : "";
                final String type = SECURITYTYPE_STOCK;
                StockInfo infoS = data.SZ_SHARE_STOCK_INFO_MAP.get(SZCode);
                if (infoS == null) {
                    infoS = new StockInfo(SZCode, traName, engName, simName, null, null);
                    infoS.setCode(SZCode);
                    infoS.setType(type);
                    data.SZ_SHARE_STOCK_INFO_MAP.put(SZCode, infoS);
                }

                infoS.setTcName(traName);
                infoS.setScName(simName);
                infoS.setEnName(engName);
                if (info.size() == 6) {
                    final String pyName = info.get(5);
                    infoS.setPyName(pyName);
                }
                data.SZ_SHARE_INFO_LIST.add(infoS);
            }
        }

        return null;
    }
}
