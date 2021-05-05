package com.etnet.coresdk.coreStorage.data.decoder;

import java.util.Arrays;
import java.util.List;

import com.etnet.coresdk.coreModel.Decoder;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.RawData;
import com.etnet.coresdk.coreStorage.model.IndustryAnalysis;
import com.etnet.coresdk.coreStorage.model.IndustryTrace;
import com.etnet.coresdk.coreStorage.model.RegionPercentage;

public class IndustryAnalysisDecoder extends Decoder {
    static final String uniqueID = "industryanalysis";

    @Override
    public NssData decodeSnapshot(String code, RawData rawData) {
        final String value = (rawData.getData()).toString();
        IndustryTrace industryTrace = new IndustryTrace();
        for (int i = 1; i < value.length(); i++) {
            IndustryAnalysis industryAnalysis = new IndustryAnalysis();
            final List<String> values = Arrays.asList(Character.toString(value.charAt(i)).split(","));
            final String indCode = values.get(0);
            industryAnalysis.setIndCode(indCode);
            final int fundInflow = Integer.parseInt(values.get(1));
            industryAnalysis.setFundInflow(fundInflow);
            final int turnover = Integer.parseInt(values.get(2));
            industryAnalysis.setTurnover(turnover);
            final String date = values.get(3);
            industryAnalysis.setDate(date);
            List<RegionPercentage> regions = Arrays.asList(
                    new RegionPercentage("CN", values.get(6)),
                    new RegionPercentage("HK", values.get(7)),
                    new RegionPercentage("TW", values.get(8)),
                    new RegionPercentage("US", values.get(4)),
                    new RegionPercentage("EU", values.get(5)),
                    new RegionPercentage("OTHERS", values.get(9))
            );
            if (values.size() >= 11) {
                industryAnalysis.setAverage(Integer.parseInt(values.get(10)));
            }
            industryAnalysis.setRegion(regions);
            industryTrace.add(industryAnalysis);
        }
        industryTrace.setIndustryCount(value.length() - 1);

        NssData nssData = new NssData(null);
        nssData.setData(industryTrace);
        nssData.setReady(true);
        return nssData;
    }
}
