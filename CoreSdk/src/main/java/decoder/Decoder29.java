package decoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.RawData;
import coreStorage.model.AsaStorage;

import static constants.AsaConstant.LANG_EN;
import static constants.AsaConstant.LANG_SC;
import static constants.AsaConstant.LANG_TC;

public class Decoder29 extends Decoder {
    static final String uniqueID = "29";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = (rawData.getData()).toString();
        AsaStorage data = getContext().getAsaStorage();
        if (value != null && value.trim() != "") {
            final List<String> values = Arrays.asList(value.split("&&"));
            List<String> engList = new ArrayList<String>();
            List<String> scList = new ArrayList<String>();
            List<String> tcList = new ArrayList<String>();

            for (String val : values) {
                final List<String> names = Arrays.asList(val.split(","));
                if (names != null && names.size() == 3) {
                    final String engName = names.get(0);
                    final String scName = names.get(1);
                    final String tcName = names.get(2);

                    engList.add(engName);
                    scList.add(scName);
                    tcList.add(tcName);
                }
            }
            data.ISSUMAP.put(LANG_EN, engList);
            data.ISSUMAP.put(LANG_SC, scList);
            data.ISSUMAP.put(LANG_TC, tcList);
        }
        return null;
    }
}
