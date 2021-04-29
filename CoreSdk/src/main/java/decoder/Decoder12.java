package decoder;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.RawData;
import coreStorage.model.AsaStorage;

public class Decoder12 extends Decoder {
    public static final String uniqueID = "12";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = (rawData.getData()).toString();
        AsaStorage data = getContext().getAsaStorage();
        if (value != null && value.trim() != "") {
            data.BLOCKTRADE = Double.parseDouble(value);
            data.BLOCKTRADENUM = data.BLOCKTRADE * 1000;
            data.BLOCKTRADENUM_EX = data.BLOCKTRADE;
        }
        return null;
    }
}
