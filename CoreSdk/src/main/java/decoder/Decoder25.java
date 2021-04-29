package decoder;

import java.util.Arrays;
import java.util.List;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.RawData;
import coreStorage.model.AsaStorage;

public class Decoder25 extends Decoder {
    public static final String uniqueID = "25";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = (rawData.getData()).toString();
        AsaStorage data = getContext().getAsaStorage();
        if (value != null && value.trim() != "") {
            // Init map and list
            final List<String> values = Arrays.asList(value.split("\\$"));

            for (String val : values) {
                final List<String> codeName = Arrays.asList(val.split(":"));
                if (codeName != null && codeName.size() == 2) {
                    final String tipsCode = codeName.get(0);
                    final String tipsName = codeName.get(1);
                    data.TIPS_NAME_MAP.put(tipsCode, tipsName);
                } else {
                }
            }
        }

        return null;
    }
}
