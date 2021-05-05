package com.etnet.coresdk.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.etnet.coresdk.coreModel.DecoderInfo;
import com.etnet.coresdk.coreModel.NssCoreContext;

public class DecodeHelper {
    public static Map<String, String> _denormalizedCodes = new HashMap<String, String>(); // normalizedCode =>
    // denormalizedCode

    public Map<String, String> getDenormalizedCodes() {
        return this._denormalizedCodes;
    }

    public void setDenormalizedCodes(Map<String, String> denormalizedCodes) {
        this._denormalizedCodes = denormalizedCodes;
    }

    public static String normalizeCode(String denormalizedCode) {
        String matched = "";

        for (String key : _denormalizedCodes.keySet()) {
            if (_denormalizedCodes.get(key) == denormalizedCode) {
                matched = _denormalizedCodes.get(key);
            }
        }
        if (matched != "") {
            return matched;
        }
        return denormalizedCode;
    }

    public static String denormalizeCode(String normalizedCode, Map<String, List<String>> futureMonthMap) {
        String code = "";
        switch (getCodeType(normalizedCode)) {
            case "FUTURE":
                String denormalized =
                        normalizedCode.substring(1, normalizedCode.length() - 1);
                int offset = Math.abs(Integer.parseInt(normalizedCode.substring(normalizedCode.length() - 1))) - 1;
                if (futureMonthMap.get(denormalized) != null) {
                    Collections.sort(futureMonthMap.get(denormalized));

                    String futureMonth = futureMonthMap.get(denormalized).get(offset);
                    denormalized = denormalized + "." + futureMonth;
                    _denormalizedCodes.put(normalizedCode, denormalized);
                    code = denormalized;
                }
                break;
            default:
                code = normalizedCode;
                break;
        }
        return code;
    }

    public static String getCodeType(String code) {
        if (Character.toString(code.charAt(0)) == "F" && !code.contains("FOREX") && !code.contains(".")) {
            return "FUTURE";
        } else {
            return "STOCK";
        }
    }

    public static List<String> getFieldId(Map<String, DecoderInfo> decoderConfigs, String fieldId){
        if (decoderConfigs.containsKey(fieldId)) {
            return decoderConfigs.get(fieldId).getServerFieldIds();
        } else {
            return null;
        }
    }

    public static List<String> getServerFieldIds(
            List<String> clientFieldIds, NssCoreContext context) {
        List<String> serverFieldIds = new ArrayList<String>();
        Map<String, DecoderInfo> decoderConfigs = context.getDecoderConfig().getConfig();



        for (String clientFieldId : clientFieldIds){
            List<String> fieldIds = getFieldId(decoderConfigs, clientFieldId);
            for (String serverFieldId : fieldIds){
                if (!serverFieldIds.contains(serverFieldId)){
                    serverFieldIds.add(serverFieldId);
                }
            }
        }
        return serverFieldIds;
    }
}
