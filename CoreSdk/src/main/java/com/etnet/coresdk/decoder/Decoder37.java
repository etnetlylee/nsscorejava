package com.etnet.coresdk.decoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.etnet.coresdk.coreModel.Decoder;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.RawData;
import com.etnet.coresdk.coreStorage.model.AsaStorage;
import com.etnet.coresdk.util.AsaDataHelper;

import static com.etnet.coresdk.constants.AsaConstant.SCREENLIST_DUALFUTURESQUOTE;
import static com.etnet.coresdk.constants.AsaConstant.SCREENLIST_FUTURESQUOTE;

public class Decoder37 extends Decoder {
    static final String uniqueID = "37";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = (rawData.getData()).toString();
        AsaStorage data = getContext().getAsaStorage();
        if (value != null && value.trim() != "") {
            final List<String> values = Arrays.asList(value.split("\\$"));

            for (String val : values) {
                final String future = val.split(":")[0];
                final String monthDate = val.split(":")[1];
                final List<String> monthDates = Arrays.asList(monthDate.split("|"));
                final List<String> monthList = new ArrayList<String>();
                for (String date : monthDates) {
                    final String monthDi = date.split(".")[1];
                    if (monthDi.split(",").length == 2) {
                        final String month = monthDi.split(",")[0];
                        final String di = monthDi.split(",")[1];
                        data.DECI_MAP_FROM_ASA.put(future, di);
                        data.DECI_MAP_FROM_ASA_FUTURE.put(future, di);
                        monthList.add(month);
                    } else {
                        final String month = monthDi;
                        monthList.add(month);
                    }
                }

                if (monthList.size() > 0) {
                    data.FUTURE_MONTH_MAP_FROM_ASA.put(future, monthList);
                }
            }

            // To handle stock future
            boolean isOption = false;
            AsaDataHelper.futureOptionListHandler(
                    data,
                    data.FUTURE_MONTH_MAP_FROM_ASA,
                    SCREENLIST_FUTURESQUOTE,
                    isOption,
                    this.getContext());
            AsaDataHelper.futureOptionListHandler(
                    data,
                    data.FUTURE_MONTH_MAP_FROM_ASA,
                    SCREENLIST_DUALFUTURESQUOTE,
                    isOption,
                    this.getContext());
        }

        return null;
    }
}
