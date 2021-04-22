package coreStorage.model;

import java.util.HashMap;
import java.util.Map;

public class OptionDataMap extends FieldStruct {
    Map<Double, OptionData> _optionMap = new HashMap<Double, OptionData>();

    public Map<Double, OptionData> getMap() {
        return this._optionMap;
    }

    public OptionData getOptionData(Double price) {
        return this._optionMap.get(price);
    }

    public void updateMap(Double price, OptionData update) {
        OptionData struct = this._optionMap.get(price);
        if (struct == null) {
            struct = new OptionData();
            this._optionMap.put(price, struct);
        }
        String put = update.getPutValue();
        String call = update.getCallValue();
        if (put != null && put != "") {
            struct.setPutValue(put);
        }
        if (call != null && call != "") {
            struct.setCallValue(call);
        }
    }

    public void addToMap(Double price, OptionData optionStruct) {
        this._optionMap.put(price, optionStruct);
    }

    public void setOptionMap(Map<Double, OptionData> optionMap) {
        this._optionMap = optionMap;
    }
}
