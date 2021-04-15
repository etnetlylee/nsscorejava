package decoder;

import java.util.Arrays;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.RawData;

public class Decoder3 extends Decoder {
    static final String uniqueID = "3";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = (rawData.getData()).toString();
        if (value != null && value.trim() != "") {
            getContext().getAsaStorage().CBBC_EXP_LIST = Arrays.asList(value.split("|"));
        }

        return null;
    }
}
