package decoder;

import java.util.Arrays;
import java.util.List;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.RawData;
import coreStorage.model.AsaStorage;
import model.Industry;
import model.StockInfo;

public class Decoder63 extends Decoder {
    static final String uniqueID = "63";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = (rawData.getData()).toString();
        AsaStorage data = getContext().getAsaStorage();
        if (value != null && value.trim() != "") {
            final List<String> values = Arrays.asList(value.split("|"));
            for (String industryInfo : values) {
                final List<String> infos = Arrays.asList(industryInfo.split(","));
                final String indCode = infos.get(0);
                final String tcName = infos.get(1);
                final String scName = infos.get(2);
                final String enName = infos.get(3);
                String codesInfo = null;
                if (infos.size() == 5) {
                    codesInfo = infos.get(4);
                }
                Industry info = new Industry();
                info.setIndustryCode(indCode);
                info.setTraName(tcName);
                info.setEngName(enName);
                info.setSimName(scName);

                int i = 0;
                if (codesInfo != null) {
                    final List<String> aCodeList = Arrays.asList(codesInfo.split(" "));
                    info.setCodeList(aCodeList);
                    for (String aCode : aCodeList) {
                        StockInfo infoAShare = data.SH_SHARE_STOCK_INFO_MAP.get(aCode);
                        if (infoAShare == null) {
                            infoAShare = new StockInfo(aCode, null, null, null, null, null);
                            data.SH_SHARE_STOCK_INFO_MAP.put(aCode, infoAShare);
                        }
                        info.setIndustryCode(indCode);
                        data.SH_SHARE_CODE_INDUSTRY_MAP.put(aCode, indCode);
                        if (data.SH_SHARE_CONSTATE_ALIST.contains(aCode) &&
                                data.SH_SHARE_CONNECT_STATES_MAP.get(aCode)) {
                            i++;
                        }
                    }
                    data.SH_SHARE_NUMBER_FOR_INDUSTRY_MAP.put(indCode, i);
                    data.SH_SHARE_INDUSTRY_CODES_MAP.put(indCode, aCodeList);
                } else {
                    data.SH_SHARE_NUMBER_FOR_INDUSTRY_MAP.put(indCode, 0);
                }
                data.SH_SHARE_CODE_INDUSTRYINFO_MAP.put(indCode, info);
                data.SH_SHARE_INDUSTRY_CODE_INFO_LIST.add(info);
                data.SH_SHARE_INDUSTRY_LIST.add(indCode);
            }
        }
        return null;
    }
}
