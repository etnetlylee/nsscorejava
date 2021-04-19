package decoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.RawData;
import coreStorage.model.AsaStorage;

public class Decoder17 extends Decoder {
    static final String uniqueID = "17";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = (rawData.getData()).toString();
        AsaStorage data = getContext().getAsaStorage();
        data.TRADING_DAY_LIST = new ArrayList<Integer>();

        if (value != null && value.trim() != "") {
            final List<String> values = Arrays.asList(value.split("|"));
            for (String val : values){
                data.TRADING_DAY_LIST.add(Integer.parseInt(val, 10));
            }
        }
        return null;
    }
}
