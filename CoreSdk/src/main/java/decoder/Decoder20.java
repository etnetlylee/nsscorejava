package decoder;

import java.util.Arrays;
import java.util.List;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.RawData;

import static constants.SecurityType.SECURITYTYPE_ETF;

public class Decoder20 extends Decoder {
    static final String uniqueID = "20";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = (rawData.getData()).toString();
        if (value != null) {
            // TODO: ???
            // getContext().getAsaStorage()[code] = value;
        }
        return null;
    }
}
