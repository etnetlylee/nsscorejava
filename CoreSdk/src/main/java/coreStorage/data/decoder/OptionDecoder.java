package coreStorage.data.decoder;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.QuoteData;
import coreModel.RawData;
import coreStorage.model.OptionData;
import coreStorage.model.OptionDataMap;

public class OptionDecoder extends Decoder {
    public static final String uniqueID = "option";
    final Logger log = Logger.getLogger("OptionDecoder");
    
    final String TARGET_C = "C";
    final String TARGET_P = "P";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        QuoteData cached =
                getContext().getStorage().getQuoteData(code, rawData.getFieldID());
        NssData nssData;
        final String value = (rawData.getData()).toString();
        String shortCode;
        if (code.length() > 14 && code.contains(".") && code.contains("_")) {
            shortCode = code.substring(0, code.indexOf("_") + 1);
        } else {
            shortCode = code;
        }

        OptionDataMap optionMap = new OptionDataMap();
        if (cached != null) {
            nssData = cached.getNssData();
            optionMap = (OptionDataMap) nssData.getData();
        } else {
            nssData = new NssData(null);
            getContext()
                    .getStorage()
                    .addQuoteData(shortCode, rawData.getFieldID(), nssData);
        }

        if (value.trim() == "") {
            return new NssData(null);
        }

        if (optionMap == null) {
            optionMap = new OptionDataMap();
            nssData.setData(optionMap);
        }

        OptionDataMap map = new OptionDataMap();
        if (!rawData.isSnapshot()) {
            final List<String> datas = Arrays.asList(value.split("$"));
            for (String data: datas){
                final String target = data.substring(0, 1);
                final String realData = data.substring(1, data.length());
                final List<String> realDatas = Arrays.asList(realData.split(":"));
                final double strike = Double.parseDouble(realDatas.get(0));
                OptionData optionStruct = optionMap.getOptionData(strike);
                OptionData option = new OptionData();
                if (optionStruct == null) {
                    optionStruct = new OptionData();
                    optionMap.addToMap(strike, optionStruct);
                }
                String realV;
                if (realDatas.size() == 2) {
                    realV = realDatas.get(1);
                    if (target == TARGET_C) {
                        optionStruct.setCallValue(realV);
                        option.setCallValue(realV);
                    } else if (target == TARGET_P) {
                        optionStruct.setPutValue(realV);
                        option.setPutValue(realV);
                    }
                    map.updateMap(strike, option);
                }
            }

        } else {
            if (value != null) {
                List<String> strickInfo = Arrays.asList(rawData.getCode().split("_"));
                if (strickInfo.size() == 2) {
                    final String target = strickInfo.get(1).substring(0, 1);
                    final String strike =
                            strickInfo.get(1).substring(1, strickInfo.get(1).length());
                    final double strikeD = Double.parseDouble(strike);
                    OptionData optionStruct = optionMap.getOptionData(strikeD);
                    if (optionStruct == null) {
                        optionStruct = new OptionData();
                        optionMap.addToMap(strikeD, optionStruct);
                    }
                    OptionData option = new OptionData();
                    if (target == this.TARGET_C) {
                        optionStruct.setCallValue(value);
                        option.setCallValue(value);
                    } else if (target == this.TARGET_P) {
                        optionStruct.setPutValue(value);
                        option.setPutValue(value);
                    }
                    map.updateMap(strikeD, option);
                } else {
                    log.info("code is not an option");
                }
            }
        }
        nssData.setData(map);
        nssData.setReady(true);
        return nssData;
    }
}
