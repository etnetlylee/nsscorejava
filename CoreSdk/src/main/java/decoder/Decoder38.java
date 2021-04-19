package decoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.RawData;
import coreStorage.model.AsaStorage;
import util.AsaDataHelper;

import static constants.AsaConstant.SCREENLIST_OPTIONSQUOTE;

public class Decoder38 extends Decoder {
    static final String uniqueID = "38";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = (rawData.getData()).toString();
        AsaStorage data = getContext().getAsaStorage();
        if (value != null && value.trim() != "") {
            final List<String> values = Arrays.asList(value.split("\\$"));
            for (String val : values) {
                final String option = val.split(":")[0];
                final String monthDate = val.split(":")[1];
                final List<String> monthDates = Arrays.asList(monthDate.split("|"));
                final List<String> monthList = new ArrayList<String>();
                for (String date : monthDates) {
                    final String monthDi = date.split(".")[1];
                    if (monthDi.split(",").length == 2) {
                        final String month = monthDi.split(",")[0];
                        final String di = monthDi.split(",")[1];
                        data.DECI_MAP_FROM_ASA.put(option, di);
                        data.DECI_MAP_FROM_ASA_OPTION.put(option, di);
                        monthList.add(month);
                    } else {
                        monthList.add(monthDi);
                    }
                }
                data.OPTION_MONTH_MAP_FROM_ASA.put(option, monthList);
            }


            // To handle stock option
            final boolean isOption = true;
            AsaDataHelper.futureOptionListHandler(
                    data,
                    data.OPTION_MONTH_MAP_FROM_ASA,
                    SCREENLIST_OPTIONSQUOTE,
                    isOption,
                    this.getContext());
        }

        return null;
    }
}
