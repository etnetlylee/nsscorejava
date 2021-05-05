package com.etnet.coresdk.util;

import com.etnet.coresdk.api.ApiFields;
import com.etnet.coresdk.constants.SecurityID;

public class ChartHelper {
    public static String getRequestPeriod(int codeGenericType, String period,
                                          boolean forHttp) {
        boolean isIndex = codeGenericType == SecurityID.SECURITYID_INDEX;
        String realPeriod = period;
        switch (period) {
            case "1I":
                realPeriod = isIndex
                        ? (forHttp == true ? "1" : ApiFields.FID_NS_1MIN)
                        : (forHttp == true ? "1" : ApiFields.FID_S_1MIN);
                break;
            case "5I":
                realPeriod = isIndex
                        ? (forHttp == true ? "5" : ApiFields.FID_NS_5MIN)
                        : (forHttp == true ? "5" : ApiFields.FID_S_5MIN);
                break;
            case "15I":
                realPeriod = isIndex
                        ? (forHttp == true ? "15" : ApiFields.FID_NS_15MIN)
                        : (forHttp == true ? "15" : ApiFields.FID_S_15MIN);
                break;
            case "30I":
                realPeriod = isIndex
                        ? (forHttp == true ? "30" : ApiFields.FID_NS_30MIN)
                        : (forHttp == true ? "30" : ApiFields.FID_S_30MIN);
                break;
            case "60I":
                realPeriod = isIndex
                        ? (forHttp == true ? "60" : ApiFields.FID_NS_60MIN)
                        : (forHttp == true ? "60" : ApiFields.FID_S_60MIN);
                break;
            case "1D":
                realPeriod = isIndex
                        ? (forHttp == true ? "103" : ApiFields.FID_NS_DAY)
                        : (forHttp == true ? "100" : ApiFields.FID_S_DAY);
                break;
            case "1W":
                realPeriod = isIndex
                        ? (forHttp == true ? "104" : ApiFields.FID_NS_WEEK)
                        : (forHttp == true ? "101" : ApiFields.FID_S_WEEK);
                break;
            case "1M":
                realPeriod = isIndex
                        ? (forHttp == true ? "105" : ApiFields.FID_NS_MONTH)
                        : (forHttp == true ? "102" : ApiFields.FID_S_MONTH);
                break;
            default:
                break;
        }
        return realPeriod;
    }

    public static String makeClientFieldID(
            String period, boolean tradingDayOnly, int range, boolean snapshot) {
        return period +
                "," +
                (tradingDayOnly ? "T" : "F") +
                "," +
                range +
                "," +
                (snapshot ? "T" : "F");
    }
}
