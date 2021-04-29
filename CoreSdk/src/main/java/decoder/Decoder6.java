package decoder;

import java.util.Arrays;
import java.util.List;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.RawData;
import coreStorage.model.AsaStorage;

public class Decoder6 extends Decoder {
    public static final String uniqueID = "6";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = (rawData.getData()).toString();
        AsaStorage data = getContext().getAsaStorage();

        if (value != null && value.trim() != "") {
            final List<String> values = Arrays.asList(value.split("|"));
            for (String val : values) {
                final List<String> valueCodes = Arrays.asList(val.split(","));
                if (valueCodes.size() > 1) {
                    final List<String> relatedCodes = Arrays.asList(valueCodes.get(1).split(" "));
                    if (data.RELATE_STOCKMAP.containsKey(valueCodes.get(0))) {
                        data.RELATE_STOCKMAP.put(valueCodes.get(0), relatedCodes);
                    }
                }
            }
        }
        return null;
    }
}
