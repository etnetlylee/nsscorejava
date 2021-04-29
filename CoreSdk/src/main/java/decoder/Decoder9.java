package decoder;

import java.util.Arrays;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.RawData;

public class Decoder9 extends Decoder {
    public static final String uniqueID = "9";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = (rawData.getData()).toString();
        if (value != null && value.trim() != "") {
            getContext().getAsaStorage().ADR_LIST = Arrays.asList(value.split("|"));
        }
        return null;
    }
}
