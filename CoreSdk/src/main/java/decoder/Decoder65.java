package decoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.RawData;
import coreStorage.model.AsaStorage;

public class Decoder65 extends Decoder {
    static final String uniqueID = "65";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = (rawData.getData()).toString();
        AsaStorage data = getContext().getAsaStorage();
        data.ASHARE_SH_TRADINGDAY_LIST = new ArrayList<Integer>();
        if (value != null && value.trim() != "") {
            final List<String> values = Arrays.asList(value.split("|"));
            for (String val : values) {
                int d = Integer.parseInt(val, 10);
                data.ASHARE_SH_TRADINGDAY_LIST.add(d);
            }
        }

        return null;
    }
}
