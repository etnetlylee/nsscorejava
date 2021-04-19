package decoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.RawData;
import coreStorage.model.AsaStorage;
import model.IndustryBaseInfo;
import model.IndustryInfo;
import model.StockInfo;

public class Decoder11 extends Decoder {
    static final String uniqueID = "11";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = (rawData.getData()).toString();
        AsaStorage data = getContext().getAsaStorage();
        data.INDUSTRY_SC_LIST = new ArrayList<String>();
        data.INDUSTRY_TC_LIST = new ArrayList<String>();
        data.INDUSTRY_EN_LIST = new ArrayList<String>();
        data.INDUSTRY_LIST = new ArrayList<String>();
        data.INDUSTRY_CODE_LIST = new ArrayList<IndustryBaseInfo>();

        int codeCount = 0;
        if (value != null && value.trim() != "") {
            final List<String> values = Arrays.asList(value.split("|"));
            data.STOCK_INFO_MAP =
                    data.STOCK_INFO_MAP != null ? data.STOCK_INFO_MAP : new HashMap<String, StockInfo>();

            for (String val : values) {
                final List<String> valuess = Arrays.asList(val.split(","));
                IndustryInfo industryInfo = new IndustryInfo();
                industryInfo.setCode(valuess.get(0));
                industryInfo.setTcName(valuess.get(1));
                industryInfo.setEnName(valuess.get(3));
                industryInfo.setScName(valuess.get(2));
                if (industryInfo.getCode().length() != 2) {
                    data.INDUSTRY_SC_LIST.add(industryInfo.getScName());
                    data.INDUSTRY_TC_LIST.add(industryInfo.getTcName());
                    data.INDUSTRY_EN_LIST.add(industryInfo.getEnName());
                    data.INDUSTRY_LIST.add(industryInfo.getCode());
                    IndustryBaseInfo industryCode = new IndustryBaseInfo();
                    industryCode.setCode(industryInfo.getCode());
                    industryCode.setTcName(industryInfo.getTcName());
                    industryCode.setEnName(industryInfo.getEnName());
                    industryCode.setScName(industryInfo.getScName());
                    data.INDUSTRY_CODE_LIST.add(industryCode);
                }
                List<String> codeList = new ArrayList<String>();
                if (valuess.size() == 5) {
                    codeList = Arrays.asList(valuess.get(4).split(" "));
                    codeCount += codeList.size();
                    data.INDUSTRY_CODES_MAP.put(industryInfo.getCode(), codeList);

                    for (String codeItem : codeList) {
                        data.INDUSTRY_CODE_MAP.put(codeItem, industryInfo.getCode());
                        StockInfo stockInfo = data.STOCK_INFO_MAP.get(codeItem);
                        if (stockInfo == null) {
                            stockInfo = new StockInfo(industryInfo.getCode(), null, null, null, null, null);
                            data.STOCK_INFO_MAP.put(codeItem, stockInfo);
                        }
                    }
                }
                industryInfo.setCodeList(codeList);
                data.INDUSTRY_MAP.put(industryInfo.getCode(), industryInfo);
            }
        }
        return null;
    }
}
