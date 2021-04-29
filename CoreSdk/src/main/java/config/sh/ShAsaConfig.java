package config.sh;

import java.util.ArrayList;
import java.util.HashMap;

import coreConfigInterface.AsaDecodersConfig;
import coreModel.AsaDecoder;
import decoder.Decoder60;
import decoder.Decoder61;
import decoder.Decoder62;
import decoder.Decoder63;
import decoder.Decoder65;


import static constants.AsaConstant.ASA_FIELD_HK;
import static constants.AsaConstant.RESOURCE_ASHARE_SH_TRADINGDAY_PLUS;
import static constants.AsaConstant.RESOURCE_SH_CODE_LIST;
import static constants.AsaConstant.RESOURCE_SH_INDEX;
import static constants.AsaConstant.RESOURCE_SH_INDUSTRY;
import static constants.AsaConstant.RESOURCE_SH_STATE_LIST;


public class ShAsaConfig {
    public final AsaDecodersConfig ShAsaConfig = new AsaDecodersConfig(
            ASA_FIELD_HK,
            new HashMap<String, AsaDecoder>() {{
                put(RESOURCE_SH_CODE_LIST, new AsaDecoder(RESOURCE_SH_CODE_LIST, "", Decoder60.uniqueID,
                        new Decoder60()));
                put(RESOURCE_SH_STATE_LIST, new AsaDecoder(RESOURCE_SH_STATE_LIST, "", Decoder61.uniqueID,
                        new Decoder61()));
                put(RESOURCE_SH_INDEX, new AsaDecoder(RESOURCE_SH_INDEX, "", Decoder62.uniqueID, new Decoder62()));
                put(RESOURCE_SH_INDUSTRY, new AsaDecoder(RESOURCE_SH_INDUSTRY, "", Decoder63.uniqueID,
                        new Decoder63()));
                put(RESOURCE_ASHARE_SH_TRADINGDAY_PLUS, new AsaDecoder(RESOURCE_ASHARE_SH_TRADINGDAY_PLUS, "",
                        Decoder65.uniqueID, new Decoder65()));
            }},
            new ArrayList<String>()
    );
}
