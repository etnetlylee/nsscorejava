package decoder;

import java.util.Arrays;
import java.util.List;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.RawData;
import coreStorage.model.AsaStorage;
import model.StockInfo;
import util.DataHelper;

import static constants.SecurityType.SECURITYTYPE_STOCK;

public class Decoder60 extends Decoder {
    static final String uniqueID = "60";

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
                final String SHCode = info.get(0);
                if (!SHCode.contains(".")) {
                    continue;
                }
                final String trimmedSHCode = SHCode.split(".")[1];
                final String target = info.get(1);
                if (DataHelper.binarySearch(data.SH_SHARE_LIST, SHCode) == -1) {
                    DataHelper.insertSorted(data.SH_SHARE_LIST, SHCode);
                }
                if (DataHelper.binarySearch(data.VALIDCODE, trimmedSHCode) == -1) {
                    DataHelper.insertSorted(data.VALIDCODE, trimmedSHCode);
                }
                if (DataHelper.binarySearch(data.SH_SHARE_LIST, trimmedSHCode) == -1) {
                    DataHelper.insertSorted(data.SH_SHARE_LIST, trimmedSHCode);
                }
                data.STKTYPEMAP.put(SHCode, SECURITYTYPE_STOCK);
                if (target != "A") {
                }

                final String traName = info.get(2) != null ? info.get(2) : "";
                final String simName = info.get(3) != null ? info.get(3) : "";
                final String engName = info.get(4) != null ? info.get(4) : "";
                final String type = SECURITYTYPE_STOCK;

                StockInfo infoS = data.SH_SHARE_STOCK_INFO_MAP.get(SHCode);
                if (infoS == null) {
                    infoS = new StockInfo(SHCode, traName, engName, simName, null, null);
                    infoS.setCode(SHCode);
                    infoS.setType(type);
                    data.SH_SHARE_STOCK_INFO_MAP.put(SHCode, infoS);
                }

                infoS.setTcName(traName);
                infoS.setScName(simName);
                infoS.setEnName(engName);
                if (info.size() == 6) {
                    final String pyName = info.get(5);
                    infoS.setPyName(pyName);
                }
                data.SH_SHARE_INFO_LIST.add(infoS);
            }
        }

        return null;
    }
}
