package decoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.RawData;
import coreStorage.model.AsaStorage;

import static constants.SecurityType.SECURITYTYPE_ETF;

public class Decoder19 extends Decoder {
    static final String uniqueID = "19";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = (rawData.getData()).toString();
        if (value != null && value.trim() != "") {
            final List<String> etfList = Arrays.asList(value.split("|"));
            for (String v : etfList) {
                getContext().getAsaStorage().STKTYPEMAP.put(v, SECURITYTYPE_ETF);
            }
        }

        return null;
    }
}
