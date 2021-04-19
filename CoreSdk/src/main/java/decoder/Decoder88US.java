package decoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.RawData;
import coreStorage.model.AsaStorage;

public class Decoder88US extends Decoder {
    static final String uniqueID = "88US";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = (rawData.getData()).toString();
        AsaStorage data = getContext().getAsaStorage();
        // BugFixed, Problem occurred in viutv, need clear this list before add.
        data.TRADING_DAY_LIST_20 = new ArrayList<Integer>();

        if (value != null && value.trim() != "") {
            final List<String> values = Arrays.asList(value.split("|"));
            for (int i = 0; i < values.size(); i++) {
                final int d = Integer.parseInt(values.get(i), 10);
                data.TRADING_DAY_LIST_20.add(d);
            }
        }
        return null;
    }
}
