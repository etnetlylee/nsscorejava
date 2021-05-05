package com.etnet.coresdk.config.sz;

import java.util.ArrayList;
import java.util.HashMap;

import com.etnet.coresdk.coreConfigInterface.AsaDecodersConfig;
import com.etnet.coresdk.coreModel.AsaDecoder;
import com.etnet.coresdk.decoder.Decoder70;
import com.etnet.coresdk.decoder.Decoder71;
import com.etnet.coresdk.decoder.Decoder73;
import com.etnet.coresdk.decoder.Decoder75;

import static com.etnet.coresdk.constants.AsaConstant.ASA_FIELD_HK;
import static com.etnet.coresdk.constants.AsaConstant.RESOURCE_ASHARE_SZ_TRADINGDAY_PLUS;
import static com.etnet.coresdk.constants.AsaConstant.RESOURCE_SZ_CODE_LIST;
import static com.etnet.coresdk.constants.AsaConstant.RESOURCE_SZ_INDUSTRY;
import static com.etnet.coresdk.constants.AsaConstant.RESOURCE_SZ_STATE_LIST;


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
