package decoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.RawData;
import coreStorage.model.AsaStorage;

public class Decoder40 extends Decoder {
    static final String uniqueID = "40";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = (rawData.getData()).toString();
        AsaStorage data = getContext().getAsaStorage();

        data.DTDC_LIST = new ArrayList<String>();
        if (value != null && value.trim() != "") {
            final List<String> dtdc = Arrays.asList(value.split("#"));
            for (String info : dtdc) {
                final List<String> infos = Arrays.asList(info.split("|"));
                if (infos.size() >= 4) {
                    final String aStock = infos.get(0);
                    final String hStock = infos.get(2);
                    data.DTDC_MAP.put(aStock, hStock);
                    data.DTDC_VALUE_MAP.put(hStock, aStock);
                    data.DTDC_LIST.add(aStock);
                }
            }
        }
        return null;
    }
}
