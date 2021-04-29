package config.sz;

import java.util.ArrayList;
import java.util.HashMap;

import coreConfigInterface.AsaDecodersConfig;
import coreModel.AsaDecoder;
import decoder.Decoder60;
import decoder.Decoder61;
import decoder.Decoder62;
import decoder.Decoder63;
import decoder.Decoder65;
import decoder.Decoder70;
import decoder.Decoder71;
import decoder.Decoder73;
import decoder.Decoder75;

import static constants.AsaConstant.ASA_FIELD_HK;
import static constants.AsaConstant.RESOURCE_ASHARE_SH_TRADINGDAY_PLUS;
import static constants.AsaConstant.RESOURCE_ASHARE_SZ_TRADINGDAY_PLUS;
import static constants.AsaConstant.RESOURCE_SH_CODE_LIST;
import static constants.AsaConstant.RESOURCE_SH_INDEX;
import static constants.AsaConstant.RESOURCE_SH_INDUSTRY;
import static constants.AsaConstant.RESOURCE_SH_STATE_LIST;
import static constants.AsaConstant.RESOURCE_SZ_CODE_LIST;
import static constants.AsaConstant.RESOURCE_SZ_INDUSTRY;
import static constants.AsaConstant.RESOURCE_SZ_STATE_LIST;


public class SzAsaConfig {
    public final AsaDecodersConfig ShAsaConfig = new AsaDecodersConfig(
            ASA_FIELD_HK,
            new HashMap<String, AsaDecoder>() {{
                put(RESOURCE_SZ_CODE_LIST, new AsaDecoder(RESOURCE_SZ_CODE_LIST, "", Decoder70.uniqueID,
                        new Decoder70()));
                put(RESOURCE_SZ_STATE_LIST, new AsaDecoder(RESOURCE_SZ_STATE_LIST, "", Decoder71.uniqueID,
                        new Decoder71()));
                put(RESOURCE_SZ_INDUSTRY, new AsaDecoder(RESOURCE_SZ_INDUSTRY, "", Decoder73.uniqueID,
                        new Decoder73()));
                put(RESOURCE_ASHARE_SZ_TRADINGDAY_PLUS, new AsaDecoder(RESOURCE_ASHARE_SZ_TRADINGDAY_PLUS, "", Decoder75.uniqueID,
                        new Decoder75()));
            }},
            new ArrayList<String>()
    );
}
